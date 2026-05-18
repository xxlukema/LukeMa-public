SELECT
    COUNT(*)
FROM
    (
        SELECT
            pool.Investor_Id, -- AS "Investor Number",
            lp.Loan_Id, --       AS "Loan Id",
            lp.PROPERTY_SEQ_NO AS "Property_Number",
            lp.PROSPECTUS_PROPERTY_ID,
            pp.ADDRESS,
            pp.CITY,
            pp.COUNTY,
            pp.STATE,
            pp.ZIP_CODE,
            pt.DESCRIPTION AS "PROPERTY_TYPE_DESCRIPTION",
            pp.APPRAISAL_VALUE,
            pp.APPRAISAL_DT,
            pp.APPRAISAL_VALUE_CONTRIBUTION,
            pp.YEAR_BUILT,
            pp.NET_SQRFT_CONTRIBUTION,
            pp.TOTAL_UNITS_CONTRIBUTION,
            pp.GROUND_LEASE_FLAG,
            pp.LAST_RENO_YEAR,
            pp.NAME AS "PROJECT_NAME",
            Lp.Allocated_Percnt_Current
        FROM
            T_CMSA_LOAN_PROPERTY lp,
            T_CMSA_PROPERTY_CODE pc,
            T_CMSA_PROPERTY_TYPE pt,
            T_CMSA_POOL pool,
            (
                SELECT
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
                    pa.PROPERTY_ID = p.PROPERTY_ID ) pp
        WHERE
            lp.PROPERTY_ID = pp.PROPERTY_ID
        AND pp.PROPERTY_CD = pc.PROPERTY_CD
        AND pc.PROPERTY_TYPE = pt.PROPERTY_TYPE
        AND pool.LOAN_ID = lp.LOAN_ID
        ORDER BY
            1,2,3 )