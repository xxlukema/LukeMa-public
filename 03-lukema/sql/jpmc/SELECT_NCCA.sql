SELECT
    R.RELATION_ID,
    R.STMT_ID,
    R.CUSTOMER_UCN,
    R.CUSTOMER_SPN,
    R.COAST_AGRREMENT_NUMBER,
    R.GMI_ACCT_NUMBER,
    R.UPDATE_DATETIME,
    C.NCCA_ID,
    CASE
        WHEN C.is_ncca = 'Y'
        THEN 'NCCA'
        WHEN C.is_ncca = 'N'
        THEN 'Underlying'
        ELSE ''
    END agreement_type,
    T.CLEARING_HOUSE_ID
FROM
    MO_COMBINED_STMT_RELATION R
LEFT OUTER JOIN CS_COL_CUSTOMER_SETUP C
ON
    C.AGREEMENT_NUMBER = R.COAST_AGRREMENT_NUMBER
LEFT OUTER JOIN CS_CLEARING_HOUSE_TBL T
ON
    T.CH_SPN = C.CH_SPN
--LEFT OUTER JOIN CS_BNK_NM_SHORT_FRM B
    --WHERE     CUSTOMER_UCN = #customerUcn#
WHERE
    c.status_code IN ('A', 'N')
AND c.agreement_processing_status = 'ACTIVE'
AND c.calculation_type = 'DERIVATIVES'
AND
    (
        C.is_ncca = 'Y'
     OR
        (
            C.ncca_id IS NOT NULL
        AND C.ncca_id != 0
        AND NVL (C.ncca_stmt_flg, 'N') = 'Y'
        )
    )
    --
    --
    --
    --
    