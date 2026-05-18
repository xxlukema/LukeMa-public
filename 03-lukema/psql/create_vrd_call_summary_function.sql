------------------------------------------------------------------------------------------
-- Create the vrd call summary function. This function is called by the VRD code
-- to retrieve data that is sent to Verizon in a GUFF file.
--
-- Author: BroadCloud Development
--
-- Usage:	
--   psql -h [Hostname] -d [Database] -U att_billing_user 
--     -f create_vrd_call_summary_function.sql 
--     -v rm_schema=[Rialto Market Schema]
------------------------------------------------------------------------------------------
-- TODO: Add better error handling by wrapping in explicit transaction
--       Add better console messages/logging
--       Check that pg_temp objects are deleted when the session ends

CREATE FUNCTION pg_temp.create_vrd_call_summary(_rm_schema text)
    RETURNS void AS
$create_vrd_call_summary$
BEGIN
    EXECUTE format('CREATE OR REPLACE FUNCTION vrd_guff.vrd_call_summary()
        returns TABLE (id INTEGER,
                      venderName CHAR(3),
                      program CHAR(6),
                      aggregationIndicator INT,
                      externalServiceFeatureInstanceID text,
                      externalTertiaryID text,
                      externalProductID text,
                      externalFeatureID text,
                      periodStart text,
                      periodEnd text,
                      quantity DECIMAL(8,1),
                      unitOfMeasure CHAR(6),
                      unitPrice text,
                      totalPrice text,
                      currencyIndicator text,
                      field1 text,
                      field2 text,
                      field3 text,
                      field4 text,
                      field5 text,
                      field6 text,
                      field7 text,
                      field8 text,
                      field9 text,
                      field10 text )
    AS
        $$
        SELECT
            CAST(nextval(''vrd_guff.seq_vz_bass_call_detail'') AS INTEGER) AS id,
            CAST(''VCE'' AS CHAR(3))                                  AS venderName,
            CAST(''BS_VCE'' AS CHAR(6))                               AS program,
            0                                                       AS aggregationIndicator,
            cl.billing_account_number                               AS externalServiceFeatureInstanceID,
            CAST(NULL AS text)                              AS externalTertiaryID,
            CAST(NULL AS text)                              AS externalProductID,
            itrc.service_name                               AS externalFeatureID,
            to_char(tg.billing_start_date, ''YYYYMMDD'')      AS periodStart,
            to_char(tg.billing_end_date, ''YYYYMMDD'')        AS periodEnd,
            CAST(SUM(counter_seconds/60.0) AS DECIMAL(8,1)) AS quantity,
            CAST(''MINUTE'' AS CHAR(6))                       AS unitOfMeasure,
            CAST(NULL AS text)                              AS unitPrice,
            CAST(NULL AS text)                              AS totalPrice,
            CAST(NULL AS text)                              AS currencyIndicator,
            CAST(NULL AS text)                              AS field1,
            CAST(NULL AS text)                              AS field2,
            CAST(NULL AS text)                              AS field3,
            CAST(NULL AS text)                              AS field4,
            CAST(NULL AS text)                              AS field5,
            CAST(NULL AS text)                              AS field6,
            CAST(NULL AS text)                              AS field7,
            CAST(NULL AS text)                              AS field8,
            CAST(NULL AS text)                              AS field9,
            CAST(NULL AS text)                              AS field10
        FROM
            %1$s.service_profile_phones spp,
            %1$s.company_location cl,
            vrd_guff.vz_bass_call_detail tg
        JOIN
            %1$s.intl_rate_code itrc
        ON
            tg.rate_zone = itrc.rate_zone_id AND itrc.thru_date IS NULL
        WHERE
            spp.tn_id = tg.phone_number AND cl.party_id = spp.location_id
        GROUP BY
            cl.billing_account_number,
            itrc.service_name,
            tg.billing_start_date,
            tg.billing_end_date
        $$ LANGUAGE SQL;', _rm_schema);

END
$create_vrd_call_summary$ LANGUAGE plpgsql;

SELECT pg_temp.create_vrd_call_summary(:'rm_schema');
