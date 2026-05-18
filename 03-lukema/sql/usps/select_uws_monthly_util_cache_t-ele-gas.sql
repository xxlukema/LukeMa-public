SELECT DISTINCT
    facility_id,
    facility_name,
    service_type,
    fiscal_year,
    fiscal_month,
    sum_interior_sf,
    fac_count,
    sum_mon_kbtu_units,
    sply_kbtu_consump,
    sum_mon_cost,
    sply_cost
FROM
    uws_monthly_util_cache_t
WHERE
    service_type = 'STEAM'
AND SUM_MON_COST IS NOT NULL
AND facility_id IS NOT NULL
ORDER BY
    fiscal_year DESC,
    fiscal_month ASC,
    service_type ASC