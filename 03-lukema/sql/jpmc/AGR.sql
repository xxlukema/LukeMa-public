SELECT DISTINCT
    ccs.AGREEMENT_NUMBER,
    ccs.CUSTOMER_UCN,
    ccs.CUSTOMER_FULL_NAME,
    ccs.CUSTOMER_SPN,
    ccs.NCCA_ID,
    CASE
        WHEN ccs.is_ncca = 'Y'
        THEN 'NCCA'
        WHEN ccs.NCCA_ID IS NOT NULL
        THEN 'Underlying'
        ELSE NULL
    END,
    bnk.shrt_bnk_nm
FROM
    CS_COL_CUSTOMER_SETUP ccs,
    cs_bnk_nm_short_frm bnk
WHERE
    ccs.STATUS_CODE = 'A'
AND ccs.AGREEMENT_PROCESSING_STATUS = 'ACTIVE'
AND ccs.CALCULATION_TYPE = 'DERIVATIVES'
AND ccs.CUSTOMER_UCN = '803659234000'
AND ccs.cs_ucn_lead_office = bnk.cs_ucn_lead_office
AND bnk.status_code = 'A'
ORDER BY
    ccs.AGREEMENT_NUMBER