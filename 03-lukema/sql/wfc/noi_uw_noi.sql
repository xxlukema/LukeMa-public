SELECT
    pool.INVESTOR_ID,
    i.CLOSING_DT              AS securitization_Date,
    i.TRANSACTION_ID          AS Deal_Id,
    pool.LOAN_ID              AS Loan_Number,
    lp.PROSPECTUS_PROPERTY_ID AS Prosup,
    lp.PROPERTY_SEQ_NO        AS Prosup_Ext,
    fs.NOI,
    p.NOI_CONTRIBUTION AS "UW_NOI",
    CASE
        WHEN p.NOI_CONTRIBUTION IS NULL
        OR  p.NOI_CONTRIBUTION = 0
        THEN 'NoN'
        ELSE ROUND( (NVL(fs.NOI, 0) / p.NOI_CONTRIBUTION - 1) * 100, 2) || '%'
    END                 AS "Variance_FYE_vs_UW",
    fs.Statement_Period AS "fs.Statement_Period",
    fs.Data_Type        AS "fs.Data_Type"
FROM
    T_CMSA_PROPERTY p,
    T_CMSA_FINANCIAL_SUMMARY fs,
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
AND fs.Statement_Period = 2011
AND (
        fs.Data_Type='AN'
    OR  fs.Data_Type = 'YTD')
ORDER BY
    1,2,3,4,5,6,7