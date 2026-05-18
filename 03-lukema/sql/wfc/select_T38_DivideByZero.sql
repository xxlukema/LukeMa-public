-- T38
SELECT
    Q1.LOAN_ID,
    DECODE(SIGN(Q1.P_Adv-NVL(Q2.P_REC,0)),-1,0,Q1.P_Adv-NVL(Q2.P_REC,0)) PRINCIPAL_ADVANCE_AMT,
    DECODE(SIGN(Q1.I_ADV-NVL(I_REC,0)),-1,0,Q1.I_ADV-NVL(I_REC,0))       NET_INTEREST_AMT,
    PKG_CMSA_Reports_Remittance.SP_CMSA_PIOUTSTAND_FNC(q1.INVESTOR_ID,q1.LOAN_ID,TO_CHAR (SYSDATE,
    'MM'),TO_CHAR(SYSDATE,'YYYY')) TOTAL_PI_ADV_AMT
FROM
    (
        SELECT
            A.INVESTOR_ID,
            A.LOAN_ID,
            SUM(a.principal_advance) P_Adv,
            SUM(A.NET_INTEREST)      I_ADV
        FROM
            T_CMSA_PI_ADVANCE A
        WHERE
            A.ADVANCE_DT BETWEEN PKG_CMSA_REPORTS_REMITTANCE.SP_CMSA_STATUS_BEGINDT_FNC
            (A.INVESTOR_ID,A.LOAN_ID,TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) AND
            PKG_CMSA_FUNCTIONS.SP_CMSA_TrusteeRemitDt_FNC(A.INVESTOR_ID,TO_CHAR(SYSDATE, 'MM'),
            TO_CHAR(SYSDATE,'YYYY'))
        GROUP BY
            A.INVESTOR_ID,
            A.LOAN_ID) q1,
    (
        SELECT
            R.INVESTOR_ID,
            R.LOAN_ID,
            SUM(R.PRINCIPAL_RECOVERY) P_REC,
            SUM( ROUND((NVL(Principal_Recovery,0) + NVL(Interest_Recovery,0)) - ( ((NVL
            (Principal_Recovery,0) + NVL(Interest_Recovery,0)) * (A.Principal_Advance +
            A.Interest_Advance - A.Actual_PI)) / (A.Principal_Advance + A.Interest_Advance) ),2) )
            - SUM(R.PRINCIPAL_RECOVERY) I_REC
        FROM
            T_CMSA_PI_RECOVERY R,
            T_CMSA_PI_ADVANCE A
        WHERE
            R.Investor_Id = A.Investor_Id
        AND R.Loan_Id = A.Loan_Id
        AND R.Payment_Due_Dt = A.Payment_Due_Dt
        AND R.Recovery_Dt BETWEEN PKG_CMSA_REPORTS_REMITTANCE.SP_CMSA_STATUS_BEGINDT_FNC
            (R.INVESTOR_ID,R.LOAN_ID,TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) AND
            PKG_CMSA_FUNCTIONS.SP_CMSA_REPDETERDT_FNC(R.INVESTOR_ID,TO_CHAR(SYSDATE,'MM'), TO_CHAR
            (SYSDATE,'YYYY'))
        GROUP BY
            R.INVESTOR_ID,
            R.LOAN_ID) q2
WHERE
    Q1.INVESTOR_ID = Q2.INVESTOR_ID(+)
AND Q1.LOAN_ID = Q2.LOAN_ID(+)