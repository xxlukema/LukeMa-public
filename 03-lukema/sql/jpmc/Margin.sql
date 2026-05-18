SELECT
    ID_SPN as spn,
    AFIRM as firm,
    AOFFIC as office,
    AACCT as account,
    BATYPE as accountType,
    BEXCH as gmiExchangeCode,
    BCCC as combinedCommodityCode,
    BIFCT as initialMarginMultiplier,
    BEXIR as initialMargin,
    BNOV as nettOptionValue,
    BEXIR-BNOV AS initialMarginRequiredToPay
FROM
    CGMIACTF
JOIN IACLCF2_1
ON
    AFIRM = BFIRM
AND AOFFIC = BOFFIC
AND AACCT = BACCNT
UNION
SELECT
    ID_SPN,
    EFIRM  AS AFIRM,
    EOFFIC AS AOFFIC,
    EACCNT AS AACCT,
    EATYPE AS BATYPE,
    EEXCH  AS BEXCH,
    EFCSYM AS BCCC,
    1      AS BIFCT,
    EEXIM  AS BEXIR,
    0      AS BNOV,
    EEXIM  AS BIMREQ
FROM
    CGMIACTF
JOIN IACLCF5_1
ON
    AFIRM = EFIRM
AND AOFFIC = EOFFIC
AND AACCT = EACCNT
WHERE
    EATYPE <> 'LZ';