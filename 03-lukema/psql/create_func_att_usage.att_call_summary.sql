CREATE OR REPLACE FUNCTION att_usage.att_call_summary
    () returns TABLE (id INTEGER,
                      recordType CHAR(3),
                      transactionId CHAR(12),
                      actionType CHAR(16),
                      rialtoSiteId CHAR(16),
                      itemId text,
                      chargeProductId CHAR(20),
                      chargeProductIdQuantityInMinute DECIMAL(32,2)
                     )
AS
    $dbvis$
    SELECT
        CAST(nextval('att_usage.seq_att_bass_call_detail') AS INTEGER)      AS id,
        CAST('DTL' AS CHAR(3))                                              AS recordType,
        CAST('TrxId' AS CHAR(12))                                           AS transactionId,
        CAST('ONE_TIME_CHARGE' AS CHAR(16))                                 AS actionType,
        CAST(spp.location_id AS CHAR(10))                                   AS rialtoSiteId,
        CAST(NULL AS text)                                                  AS itemId,
        CAST(actt.att_sku AS CHAR(10))                                      AS chargeProductId,
        CAST(SUM(counter_seconds/60.0) AS DECIMAL(32,2))                    AS
        chargeProductIdQuantityInMinute
    FROM
        rm_release21.service_profile_phones spp,
        rm_release21.company_location cl,
        att_usage.att_bass_call_detail tg,
        att_usage.att_calltype_translation actt
    WHERE
        spp.tn_id = tg.phone_number
    AND cl.party_id = spp.location_id
    AND actt.bass_call_type = tg.call_type
    GROUP BY
        spp.location_id,
        actt.att_sku,
        tg.billing_start_date,
        tg.billing_end_date $dbvis$ LANGUAGE SQL