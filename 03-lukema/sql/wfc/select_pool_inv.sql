SELECT
    loan.LOAN_ID,
    inv.INVESTOR_ID
FROM
    T_CMSA_POOL pool,
    T_CMSA_LOAN loan,
    T_CMSA_INVESTOR inv
WHERE
    pool.LOAN_ID = loan.LOAN_ID
AND pool.INVESTOR_ID = inv.INVESTOR_ID
AND inv.INVESTOR_ID = 555