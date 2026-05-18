
--End Schedule Balance
SELECT
    SUM(PKG_CMSA_Functions.SP_CMSA_Endschbal_FNC(P.INVESTOR_ID,W.LOAN_ID,11,2012,
    NULL)) "END_SCH_BAL"
FROM
    T_CMSA_WATCHLIST W,
    T_CMSA_POOL P,
    T_CMSA_LOAN L
WHERE
    W.LOAN_ID=P.LOAN_ID
AND L.LOAN_ID=P.LOAN_ID
AND W.LOAN_ID NOT IN
    (
        SELECT
            LOAN_ID
        FROM
            V_CMSA_PAYOFF
        WHERE
            LOAN_ID = P.LOAN_ID)
AND W.LOAN_ID NOT IN
    (
        SELECT
            PL.LOAN_ID
        FROM
            T_CMSA_PERIODIC_LOAN PL,
            T_CMSA_POOL POOL
        WHERE
            PL.LOAN_ID = POOL.LOAN_ID
        AND INVESTOR_ID = 769
        AND PERIODIC_MONTH = 11
        AND PERIODIC_YEAR = 2012
        AND DEFEASANCE_CD = 'F' )
AND NOT EXISTS
    (
        SELECT
            'X'
        FROM
            T_CMSA_LOAN_SWAP
        WHERE
            LOAN_ID=P.LOAN_ID
        AND EFFECTIVE_DT<='13-NOV-12' )
AND NOT EXISTS
    (
        SELECT
            'X'
        FROM
            T_CMSA_SPECIAL_SERVICING
        WHERE
            LOAN_ID=P.LOAN_ID
        AND TRANSFER_DT<'13-NOV-12'
        AND (
                RETURN_DT IS NULL
            OR  RETURN_DT>'13-NOV-12') )
AND W.EFFECTIVE_DT<='13-NOV-12'
AND (
        W.RELEASE_DT IS NULL
    OR  W.RELEASE_DT>'13-NOV-12')
AND W.LOAN_ID IN
    (
        SELECT
            LOAN_ID
        FROM
            T_CMSA_LOAN_WATCHLIST
        WHERE
            EFFECTIVE_DT<='13-NOV-12'
        AND CRITERIA_REL_DT IS NULL )
AND P.INVESTOR_ID=769
AND Sp_Cmsa_Loan_Refcd_Criteria(W.LOAN_ID,'13-NOV-12') IS NOT NULL ;