SELECT
    COUNT(*)
FROM
    (
        SELECT DISTINCT
            p.ADDRESS,
            p.CITY,
            p.COUNTY,
            p.STATE,
            p.ZIP_CODE,
            p.APPRAISAL_VALUE_CONTRIBUTION,
            p.YEAR_BUILT,
            p.NET_SQRFT_CONTRIBUTION,
            p.TOTAL_UNITS_CONTRIBUTION,
            p.GROUND_LEASE_FLAG,
            p.LAST_RENO_YEAR,
            p.NAME AS "PROJECT_NAME",
            pa.APPRAISAL_VALUE,
            pa.APPRAISAL_DT,
            p.PROPERTY_CD,
            p.PROPERTY_ID,
            p.NAME
        FROM
            T_CMSA_PROPERTY p
        LEFT OUTER JOIN
            T_CMSA_PROPERTY_APPRAISAL pa
        ON
            pa.PROPERTY_ID = p.PROPERTY_ID )