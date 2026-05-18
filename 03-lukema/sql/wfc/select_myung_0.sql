--q2
SELECT
    R.INVESTOR_ID,
    I.INVESTOR_NAME,
    R.LOAN_ID ,
    A.ADVANCE_DT,
    A.PAYMENT_DUE_DT,
    A.Principal_Advance,
    A.Interest_Advance,
    A.Principal_Advance + A.Interest_Advance - A.Actual_PI
FROM
    T_CMSA_PI_RECOVERY R,
    T_CMSA_PI_ADVANCE A,
    T_CMSA_INVESTOR I
WHERE
    R.Investor_Id = A.Investor_Id
AND I.INVESTOR_ID = A.Investor_Id
AND R.Loan_Id = A.Loan_Id
AND R.Payment_Due_Dt = A.Payment_Due_Dt
    --
AND R.Recovery_Dt BETWEEN PKG_CMSA_REPORTS_REMITTANCE.SP_CMSA_STATUS_BEGINDT_FNC (R.INVESTOR_ID ,
    R.LOAN_ID,TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) AND
    PKG_CMSA_FUNCTIONS.SP_CMSA_REPDETERDT_FNC(R.INVESTOR_ID,TO_CHAR(SYSDATE,'MM'), TO_CHAR (SYSDATE
    ,'YYYY'))
    --
AND A.Principal_Advance + A.Interest_Advance = 0
    --
    