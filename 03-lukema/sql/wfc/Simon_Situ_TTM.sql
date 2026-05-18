SELECT
    pool.INVESTOR_ID,
    i.CLOSING_DT              AS securitization_Date,
    i.TRANSACTION_ID          AS Deal_Id,
    pool.LOAN_ID              AS Loan_Number,
    lp.PROSPECTUS_PROPERTY_ID AS Prosup,
    lp.PROPERTY_SEQ_NO        AS Prosup_Ext,
    fs.TTM_NOI,
    p.NOI_CONTRIBUTION AS "UW_NOI",
    CASE
        WHEN p.NOI_CONTRIBUTION IS NULL
        OR  p.NOI_CONTRIBUTION = 0
        THEN 'NoN'
        ELSE ROUND( (NVL(fs.TTM_NOI, 0) / p.NOI_CONTRIBUTION - 1) * 100, 2) || '%'
    END AS "Variance_TTM_vs_UW"
FROM
    T_CMSA_PROPERTY p,
    (
        SELECT
            PROPERTY_ID,
            DATA_TYPE AS TTM,
            NOI       AS TTM_NOI
        FROM
            T_CMSA_FINANCIAL_SUMMARY
        WHERE
            Property_Id IN
            (
                /** 336 PROPERTY_IDs **/
                SELECT
                    PROPERTY_ID
                FROM
                    T_CMSA_LOAN_PROPERTY
                WHERE
                    LOAN_ID IN
                    (
                        SELECT
                            LOAN_ID
                        FROM
                            T_CMSA_POOL
                        WHERE
                            INVESTOR_ID IN
                            (
                                /** 3 Inverstor_IDs **/
                                SELECT
                                    INVESTOR_ID
                                FROM
                                    T_CMSA_INVESTOR
                                WHERE
                                    CLOSING_DT >= to_date('2011', 'yyyy')
                                    /** 3 Inverstor_IDs **/
                            ))
                    /** 336 PROPERTY_IDs **/
            )
        AND Statement_Period = 2011
        AND Data_Type = 'TR') fs, -- joined
    T_CMSA_POOL pool,
    T_CMSA_LOAN_PROPERTY lp,
    T_CMSA_INVESTOR i
WHERE
    p.PROPERTY_ID = fs.PROPERTY_ID
AND pool.LOAN_ID = lp.LOAN_ID
AND lp.PROPERTY_ID = p.PROPERTY_ID
AND fs.PROPERTY_ID = p.PROPERTY_ID
AND i.INVESTOR_ID = pool.INVESTOR_ID
AND i.CLOSING_DT >= to_date('2011', 'yyyy')
    --AND fs.Statement_Period = 2011
ORDER BY
    1,2,3,4,5,6,7