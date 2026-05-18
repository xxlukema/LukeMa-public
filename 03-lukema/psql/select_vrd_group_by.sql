SELECT
    cl.billing_account_number,                       -- "External Service Feature Instance ID",
    itrc.service_name,                               -- "External Feature ID",
    CAST(SUM(counter_seconds/60.0) AS DECIMAL(8,1))  -- "Quantity"
FROM
    rm_qa_sa.service_profile_phones spp, --- 245,293 rows
    rm_qa_sa.company_location cl, --- 40,965 rows
    vrd_qa.bass_call_detail tg
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
    itrc.service_name;