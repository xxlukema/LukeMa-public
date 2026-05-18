SELECT
    LOAN_ID,
    EFFECTIVE_DT
FROM
    T_CMSA_WATCHLIST
WHERE
    EFFECTIVE_DT = to_date('2012-03-21', 'yyyy-mm-dd')
ORDER BY
    1, 2