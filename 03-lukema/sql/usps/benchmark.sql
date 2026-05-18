SELECT
    area_code,
    area_name,
    district_code,
    district_name,
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
    facility_id = '522003G01'
AND fiscal_year <> 2013
AND sum_mon_cost > 3300
AND sum_mon_cost < 3500 ;