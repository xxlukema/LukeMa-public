SELECT
    pool.INVESTOR_ID,
    lp.PROPERTY_ID
FROM
    T_CMSA_LOAN_PROPERTY lp,
    T_CMSA_POOL pool
WHERE
    pool.LOAN_ID = lp.LOAN_ID
AND lp.PROPERTY_ID IN
    (
        SELECT
            PROPERTY_ID
        FROM
            (
                SELECT
                    COUNT(pool.INVESTOR_ID),
                    lp.PROPERTY_ID
                FROM
                    T_CMSA_POOL pool,
                    T_CMSA_LOAN_PROPERTY lp
                WHERE
                    pool.LOAN_ID = lp.LOAN_ID
                GROUP BY
                    lp.PROPERTY_ID
                HAVING
                    COUNT(pool.INVESTOR_ID) > 1))
ORDER BY
    PROPERTY_ID