SELECT DISTINCT
    ccs.AGREEMENT_NUMBER,
    ccs.CUSTOMER_UCN,
    ccs.CUSTOMER_FULL_NAME,
    ccs.CUSTOMER_SPN,
    CASE
        WHEN ccs.IS_PRMS_MARGIN_CLIENT = 'Y'
        THEN ccs.CUSTOMER_FULL_NAME
        ELSE 'Not Applicable'
    END AS PRMS_FUND_MARGIN_NAME,
    ccs.NCCA_ID,
    CASE
        WHEN ccs.is_ncca = 'Y'
        THEN 'NCCA'
        WHEN ccs.NCCA_ID IS NOT NULL
        THEN 'Underlying'
        ELSE NULL
    END             AS underlyingOrNcca,
    bnk.shrt_bnk_nm AS legalEntity,
    CH.CH_NAME      AS clearingHouse
FROM
    CS_COL_CUSTOMER_SETUP ccs,
    cs_bnk_nm_short_frm bnk,
    cs_clearing_house ch
WHERE
    ccs.STATUS_CODE = 'A'
AND ccs.AGREEMENT_PROCESSING_STATUS = 'ACTIVE'
AND ccs.CALCULATION_TYPE = 'DERIVATIVES'
AND ccs.CUSTOMER_UCN = '006303911000'
AND ccs.cs_ucn_lead_office = bnk.cs_ucn_lead_office
AND bnk.status_code = 'A'
AND ccs.ch_spn = ch.ch_spn(+)
ORDER BY
    ncca_id nulls FIRST,
    underlyingOrNcca ASC nulls FIRST