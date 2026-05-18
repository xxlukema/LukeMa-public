SELECT
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
    facility_id = '258231G05'
ORDER BY
    fiscal_year DESC,
    fiscal_month DESC