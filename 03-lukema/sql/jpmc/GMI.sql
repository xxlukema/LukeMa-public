SELECT DISTINCT
    CUSTOMER_SPN,
    ACCOUNT,
    ACCOUNT_MARGIN_RULE,
    mo_utility_pkg.getcodeval ('GMI_LE' , legal_entity ) as legalEntity
FROM
    MO_GMI_ACCOUNT_VIEW
WHERE
    CUSTOMER_SPN = '5260868'
AND ACCOUNT_MARGIN_RULE = ACCOUNT_TYPE
AND COB_DATE =
    (
        SELECT
            MAX(COB_DATE)
        FROM
            MO_GMI_ACCOUNT_VIEW
    )
ORDER BY
    ACCOUNT