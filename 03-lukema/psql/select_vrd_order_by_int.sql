SELECT
    cl.billing_account_number,
    itrc.service_name,
    tg.phone_number,
    tg.rate_zone,
    spp.location_id,
    tg.start_date,
    tg.end_date
FROM
    rm_release21.service_profile_phones spp,
    rm_release21.company_location cl,
    vrd_guff.bass_call_detail tg
LEFT OUTER JOIN
    rm_release21.intl_rate_code itrc
ON
    (
        tg.rate_zone = itrc.rate_zone_id)
AND itrc.thru_date IS NULL
WHERE
    spp.location_id = cl.party_id
AND tg.phone_number = spp.tn_id
ORDER BY
    cl.billing_account_number,
    itrc.service_name;
