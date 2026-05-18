SELECT DISTINCT
    facility_id
FROM
    uws_monthly_util_cache_t
WHERE
    facility_id IS NOT NULL;