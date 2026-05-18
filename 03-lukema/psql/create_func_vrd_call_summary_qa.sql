CREATE OR REPLACE FUNCTION vrd_guff.vrd_call_summary
    () returns TABLE (id INTEGER,
                      venderName CHAR(3),
                      program CHAR(6),
                      aggregationIndicator INT,
                      externalServiceFeatureInstanceID text,
                      externalTertiaryID text,
                      externalProductID text,
                      externalFeatureID text,
                      periodStart DATE,
                      periodEnd DATE,
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
        CAST(nextval('vrd_guff.seq_bass_call_detail') AS INTEGER) AS id,
        CAST('VCE' AS CHAR(3))                                  AS venderName,
        CAST('BS_VCE' AS CHAR(6))                               AS program,
        0                                                       AS aggregationIndicator,
        cl.billing_account_number                               AS externalServiceFeatureInstanceID
        ,
        CAST(NULL AS text)                              AS externalTertiaryID,
        CAST(NULL AS text)                              AS externalProductID,
        itrc.service_name                               AS externalFeatureID,
        CAST(NULL AS DATE)                              AS periodStart,
        CAST(NULL AS DATE)                              AS periodEnd,
        CAST(SUM(counter_seconds/60.0) AS DECIMAL(8,1)) AS quantity,
        CAST('MINUTE' AS CHAR(6))                       AS unitOfMeasure,
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
        rm_qa_sa.service_profile_phones spp, --- 245,293 rows
        rm_qa_sa.company_location cl, --- 40,965 rows
        vrd_guff.bass_call_detail tg
    LEFT OUTER JOIN
        rm_qa_sa.intl_rate_code itrc
    ON
        (
            tg.rate_zone = itrc.rate_zone_id)
    AND itrc.thru_date IS NULL
    WHERE
        spp.tn_id = tg.phone_number
    AND cl.party_id = spp.location_id
    GROUP BY
        cl.billing_account_number,
        itrc.service_name $$ LANGUAGE SQL;
    
    