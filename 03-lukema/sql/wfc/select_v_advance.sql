SELECT
    POOL.INVESTOR_ID       INVESTOR_ID,
    POOL.LOAN_ID           LOAN_ID,
    COMPFEED.BORROWER_NAME LOAN_NAME,
    T36.NEXT_PYMT_DT       LAST_PYMT_DT,
    T37.FST_ADVANCE_DT,
    PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(POOL.INVESTOR_ID,TO_CHAR(SYSDATE,'MM'),TO_CHAR
    (SYSDATE,'YYYY'))                                        CURRENT_ADVANCE_DT,
    T28.BEGIN_SCH_BAL*POOL.PERCENT_OWNED                               "CURBEGIN_SCHBAL", --L6
    PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T21.TRANSFER_DT)           "TRANSFER_DT", --L77
    DECODE(T38.PRINCIPAL_ADVANCE_AMT,0,NULL,T38.PRINCIPAL_ADVANCE_AMT) PRINCIPAL_ADVANCE_AMT,
    DECODE(T38.TOTAL_PI_ADV_AMT,0,NULL,T38.NET_INTEREST_AMT)           NET_INTEREST_AMT,
    DECODE (T38.TOTAL_PI_ADV_AMT,0, NULL,T38.TOTAL_PI_ADV_AMT)         TOTAL_PI_ADV_AMT, --L37
    T29.CumASER                                                        CUMULATIVE_ASER_AMT, --L35
    PKG_CMSA_Reports_Remittance.SP_CMSA_SERVOUTSTAND_FNC(POOL.INVESTOR_ID,T21.LOAN_ID,TO_CHAR
    (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) "TOTAL_SERV_ADV_AMT",
    T40.INTEREST_ON_ADV,
    PKG_CMSA_Reports_Remittance.SP_CMSA_TIOUTSTAND_FNC(POOL.INVESTOR_ID,T21.LOAN_ID,TO_CHAR(SYSDATE
    ,'MM'),TO_CHAR(SYSDATE,'YYYY'))                               "TOTAL_TI_ADV_AMT", --L38
    T20.APPRAISAL_VALUE                                           APPRAISAL_VALUE, --L75
    PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T19.MOST_REC_APPRLDT) "MOST_REC_APPRLDT", --L74
    T35.ARA_AMT                                                   ARA_AMT, --L99
    T39.CURENDING_SCH_BAL,
    T29.ASERAMT ASERAMT --L33
FROM
    --L74
    (
        SELECT
            LP.LOAN_ID,
            DECODE(MIN(DECODE(APPRAISAL_DT,NULL,0)),0,NULL,MAX(APPRAISAL_DT)) "MOST_REC_APPRLDT"
        FROM
            T_CMSA_PROPERTY_APPRAISAL PA,
            T_CMSA_LOAN_PROPERTY LP,
            T_CMSA_POOL P
        WHERE
            P.LOAN_ID=LP.LOAN_ID
        AND LP.PROPERTY_ID=PA.PROPERTY_ID
        AND PA.APPRAISAL_DT <= PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(P.INVESTOR_ID,TO_CHAR
            (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY'))
        AND LP.PROPERTY_ID NOT IN
            (
                SELECT
                    PROPERTY_ID
                FROM
                    T_CMSA_PROPERTY_SWAP
                WHERE
                    LOAN_ID=P.LOAN_ID
                AND EFFECTIVE_DT <=PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(P.INVESTOR_ID,TO_CHAR
                    (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')))
        GROUP BY
            LP.LOAN_ID) T19,
    --L75
    (
        SELECT
            LP.LOAN_ID,
            SUM(APPRAISAL_VALUE) APPRAISAL_VALUE
        FROM
            T_CMSA_PROPERTY_APPRAISAL PA,
            T_CMSA_LOAN_PROPERTY LP,
            T_CMSA_POOL P,
            (
                SELECT
                    LP.LOAN_ID,
                    MAX(APPRAISAL_DT) APPRAISAL_DT
                FROM
                    T_CMSA_PROPERTY_APPRAISAL PA,
                    T_CMSA_LOAN_PROPERTY LP
                WHERE
                    PA.PROPERTY_ID=LP.PROPERTY_ID
                GROUP BY
                    LP.LOAN_ID) Q1
        WHERE
            P.LOAN_ID=LP.LOAN_ID
        AND PA.PROPERTY_ID=LP.PROPERTY_ID
        AND LP.LOAN_ID=Q1.LOAN_ID
        AND PA.APPRAISAL_DT <= PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(P.INVESTOR_ID,TO_CHAR
            (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY'))
        AND PA.APPRAISAL_DT=Q1.APPRAISAL_DT
        AND PA.PROPERTY_ID NOT IN
            (
                SELECT
                    PROPERTY_ID
                FROM
                    T_CMSA_PROPERTY_SWAP
                WHERE
                    LOAN_ID=LP.LOAN_ID
                AND EFFECTIVE_DT <=PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(P.INVESTOR_ID,TO_CHAR
                    (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')))
        GROUP BY
            LP.LOAN_ID ) T20,
    --L77
    (
        SELECT
            SS.LOAN_ID,
            MAX(TRANSFER_DT) TRANSFER_DT
        FROM
            T_CMSA_SPECIAL_SERVICING SS
        WHERE
            SS.RETURN_DT IS NULL
        GROUP BY
            SS.LOAN_ID) T21,
    (
        SELECT
            PL.LOAN_ID,
            (PL.BEGIN_SCH_BAL) BEGIN_SCH_BAL
        FROM
            T_CMSA_PERIODIC_LOAN PL
        WHERE
            PERIODIC_MONTH=TO_CHAR(SYSDATE,'MM')
        AND PERIODIC_YEAR=TO_CHAR(SYSDATE,'YYYY')) T28,
    (
        SELECT
            AR.INVESTOR_ID,
            AR.LOAN_ID,
            DECODE(PIAdv.Advance_Dt,NULL,0-PKG_CMSA_REPORTS_REMITTANCE.SP_CMSA_CalcLateASERPmt_FNC
            (AR.INVESTOR_ID, AR.LOAN_ID, PIR.RECOVERY_DT, TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,
            'YYYY')) ,DECODE(Q1.Rule_Code,0,0,1,(PKG_CMSA_Reports_RemitSuppTab.SP_CMSA_FindASER_FNC
            (PIAdv.Investor_Id,PIAdv.Loan_ID,PIAdv.Advance_Dt)-
            PKG_CMSA_REPORTS_REMITTANCE.SP_CMSA_CalcLateASERPmt_FNC(AR.INVESTOR_ID, AR.LOAN_ID,
            PIADV.ADVANCE_DT, TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) ),ROUND((NVL
            (PIAdv.Principal_Advance,0) + NVL(PIAdv.Net_Interest,0)) * NVL(PIAdv.ASER_Percnt,0) -
            PKG_CMSA_REPORTS_REMITTANCE.SP_CMSA_CalcLateASERPmt_FNC(AR.INVESTOR_ID, AR.LOAN_ID,
            PIADV.ADVANCE_DT, TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) ,2))) AS ASERAMT,
            PKG_CMSA_Reports_Remittance.SP_CMSA_CumASERBalance_FNC(AR.INVESTOR_ID,AR.LOAN_ID,
            TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) CumASER
        FROM
            T_CMSA_LOAN_ARA AR,
            -- replace with T_CMSA_LOAN_ASER 05/05/06
            -- T_CMSA_APPRAISAL_REDUCTION AR,
            T_CMSA_POOL P,
            T_CMSA_PI_ADVANCE PIAdv,
            -- Replace T_CMSA_APPRAISAL_REDUCTION WITH T_CMSA_LOAN_ARA 05/05/06
            (
                SELECT
                    INVESTOR_ID,
                    LOAN_ID,
                    MAX(AR.ARA_DT) ARA_DT
                FROM
                    T_CMSA_LOAN_ARA AR
                    --T_CMSA_APPRAISAL_REDUCTION AR
                WHERE
                    AR.ARA_DT <= PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(AR.INVESTOR_ID,TO_CHAR
                    (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY'))
                GROUP BY
                    INVESTOR_ID,
                    LOAN_ID) Q,
            (
                SELECT
                    IR.INVESTOR_ID ,
                    BR.Rule_Code
                FROM
                    T_CMSA_INVESTOR_RULE IR,
                    T_CMSA_BUSINESS_RULE BR
                WHERE
                    IR.Rule_Type = 1
                AND IR.Rule_Id = BR.Rule_Id )Q1,
            T_CMSA_PI_RECOVERY PIR
        WHERE
            AR.INVESTOR_ID=P.INVESTOR_ID
        AND AR.INVESTOR_ID=Q1.INVESTOR_ID
        AND AR.LOAN_ID=P.LOAN_ID
        AND AR.INVESTOR_ID=Q.INVESTOR_ID
        AND AR.LOAN_ID=Q.LOAN_ID
        AND AR.ARA_DT=Q.ARA_DT
        AND PKG_CMSA_Reports_Remittance.SP_CMSA_Status_BeginDt_FNC(AR.INVESTOR_ID,AR.LOAN_ID,
            TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) IS NOT NULL
        AND PIAdv.Investor_Id = AR.INVESTOR_ID
        AND PIAdv.Loan_Id = AR.LOAN_ID
        AND PIAdv.Advance_Dt =
            (
                SELECT
                    MAX(PI.Advance_Dt)
                FROM
                    T_CMSA_PI_ADVANCE PI
                WHERE
                    PI.Investor_Id = AR.INVESTOR_ID
                AND PI.Loan_Id = AR.LOAN_ID
                AND PI.Advance_Dt <= PKG_CMSA_Functions.Sp_Cmsa_Trusteeremitdt_Fnc(PI.INVESTOR_ID,
                    TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')))
        AND PIAdv.LOAN_ID = PIR.LOAN_ID(+)
        AND PIAdv.INVESTOR_ID = PIR.INVESTOR_ID(+)
        AND PIAdv.PAYMENT_DUE_DT = PIR.PAYMENT_DUE_DT(+) ) T29,
    (
        SELECT
            AR.INVESTOR_ID,
            AR.LOAN_ID,
            AR.ARA_AMT,
            AR.ARA_DT
        FROM
            T_CMSA_LOAN_ARA AR,
            -- replace with T_CMSA_LOAN_ARA 05/05/06
            --T_CMSA_APPRAISAL_REDUCTION AR,
            T_CMSA_POOL P,
            (
                SELECT
                    INVESTOR_ID,
                    LOAN_ID,
                    MAX(AR.ARA_DT) ARA_DT
                FROM
                    T_CMSA_LOAN_ARA AR
                    -- replace with T_CMSA_LOAN_ARA
                    -- T_CMSA_APPRAISAL_REDUCTION AR
                WHERE
                    AR.ARA_DT <= PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(AR.INVESTOR_ID,TO_CHAR
                    (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY'))
                GROUP BY
                    INVESTOR_ID,
                    LOAN_ID) Q
        WHERE
            AR.INVESTOR_ID=P.INVESTOR_ID
        AND AR.LOAN_ID=P.LOAN_ID
        AND AR.INVESTOR_ID=Q.INVESTOR_ID
        AND AR.LOAN_ID=Q.LOAN_ID
        AND AR.ARA_DT=Q.ARA_DT ) T35 ,
    (
        SELECT
            DLF.LOAN_ID,
            MAX(DLF.NEXT_PYMT_DT) NEXT_PYMT_DT
        FROM
            T_CMSA_DAILY_LOAN_FEED DLF,
            (
                SELECT
                    SS.LOAN_ID,
                    MAX(TRANSFER_DT) TRANSFER_DT
                FROM
                    T_CMSA_SPECIAL_SERVICING SS
                WHERE
                    SS.RETURN_DT IS NULL
                GROUP BY
                    SS.LOAN_ID)Q
        WHERE
            DLF.LOAN_ID=Q.LOAN_ID
        AND DLF.FEED_DT > ADD_MONTHS(SYSDATE,-2)
        GROUP BY
            DLF.LOAN_ID) T36,
    (
        SELECT
            INVESTOR_ID,
            LOAN_ID,
            MAX(IOA_BEGIN_DT) FST_ADVANCE_DT
        FROM
            T_CMSA_IOA_CALCULATION
        GROUP BY
            LOAN_ID,
            INVESTOR_ID) T37,
    (
        SELECT
            Q1.LOAN_ID,
            DECODE(SIGN(Q1.P_Adv-NVL(Q2.P_REC,0)),-1,0,Q1.P_Adv-NVL(Q2.P_REC,0))
                                                                           PRINCIPAL_ADVANCE_AMT,
            DECODE(SIGN(Q1.I_ADV-NVL(I_REC,0)),-1,0,Q1.I_ADV-NVL(I_REC,0)) NET_INTEREST_AMT,
            PKG_CMSA_Reports_Remittance.SP_CMSA_PIOUTSTAND_FNC(q1.INVESTOR_ID,q1.LOAN_ID,TO_CHAR
            (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) TOTAL_PI_ADV_AMT
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
                    PKG_CMSA_FUNCTIONS.SP_CMSA_TrusteeRemitDt_FNC(A.INVESTOR_ID,TO_CHAR(SYSDATE,
                    'MM'),TO_CHAR(SYSDATE,'YYYY'))
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
                    A.Interest_Advance - A.Actual_PI)) / (A.Principal_Advance + A.Interest_Advance)
                    ),2) ) - SUM(R.PRINCIPAL_RECOVERY) I_REC
                FROM
                    T_CMSA_PI_RECOVERY R,
                    T_CMSA_PI_ADVANCE A
                WHERE
                    R.Investor_Id = A.Investor_Id
                AND R.Loan_Id = A.Loan_Id
                AND R.Payment_Due_Dt = A.Payment_Due_Dt
                AND R.Recovery_Dt BETWEEN PKG_CMSA_REPORTS_REMITTANCE.SP_CMSA_STATUS_BEGINDT_FNC
                    (R.INVESTOR_ID,R.LOAN_ID,TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) AND
                    PKG_CMSA_FUNCTIONS.SP_CMSA_REPDETERDT_FNC(R.INVESTOR_ID,TO_CHAR(SYSDATE,'MM'),
                    TO_CHAR(SYSDATE,'YYYY'))
                GROUP BY
                    R.INVESTOR_ID,
                    R.LOAN_ID) q2
        WHERE
            Q1.INVESTOR_ID = Q2.INVESTOR_ID(+)
        AND Q1.LOAN_ID = Q2.LOAN_ID(+) )T38,
    (
        SELECT
            B.LOAN_ID,
            A.CURENDING_SCH_BAL
        FROM
            (
                SELECT
                    P.INVESTOR_ID,
                    SUM(PKG_CMSA_Functions.SP_CMSA_EndSchBal_FNC(P.INVESTOR_ID,LOAN_ID, TO_CHAR
                    (SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY'),CAL.REPORT_DETER_DT))
                    "CURENDING_SCH_BAL"
                FROM
                    T_CMSA_POOL P,
                    T_CMSA_DISTRIBUTION_CALENDAR CAL
                WHERE
                    P.INVESTOR_ID=CAL.INVESTOR_ID
                AND CAL.REPORTING_MONTH=TO_CHAR(SYSDATE,'MM')
                AND CAL.REPORTING_YEAR=TO_CHAR(SYSDATE,'YYYY')
                GROUP BY
                    P.INVESTOR_ID) A,
            (
                SELECT
                    P.INVESTOR_ID,
                    MAX(SS.LOAN_ID)LOAN_ID
                FROM
                    T_CMSA_POOL P,
                    T_CMSA_SPECIAL_SERVICING SS
                WHERE
                    P.LOAN_ID=SS.LOAN_ID
                AND SS.RETURN_DT IS NULL
                GROUP BY
                    INVESTOR_ID) B
        WHERE
            A.INVESTOR_ID=B.INVESTOR_ID) T39,
    (
        SELECT
            LOAN_ID,
            PKG_CMSA_REPORTS_REMITSUPPTAB.SP_CMSA_INTCUMADV_FNC(P.INVESTOR_ID,P.LOAN_ID,
            CAL.REPORT_DETER_DT, TO_CHAR(SYSDATE,'MM'),TO_CHAR(SYSDATE,'YYYY')) INTEREST_ON_ADV
        FROM
            T_CMSA_POOL P,
            T_CMSA_DISTRIBUTION_CALENDAR CAL
        WHERE
            P.INVESTOR_ID=CAL.INVESTOR_ID
        AND CAL.REPORTING_MONTH=TO_CHAR(SYSDATE,'MM')
        AND CAL.REPORTING_YEAR=TO_CHAR(SYSDATE,'YYYY') )T40,
    T_CMSA_POOL POOL,
    T_CMSA_COMPARISON_FEED COMPFEED
WHERE
    POOL.LOAN_ID=T21.LOAN_ID
AND COMPFEED.LOAN_ID=T21.LOAN_ID
AND T19.LOAN_ID(+)=T21.LOAN_ID
AND T20.LOAN_ID(+)=T21.LOAN_ID
AND T28.LOAN_ID(+)=T21.LOAN_ID
AND T29.LOAN_ID(+)=T21.LOAN_ID
AND T35.LOAN_ID(+)=T21.LOAN_ID
AND T36.LOAN_ID(+)=T21.LOAN_ID
AND T37.LOAN_ID(+)=T21.LOAN_ID
AND T38.LOAN_ID(+)=T21.LOAN_ID
AND T39.LOAN_ID(+)=T21.LOAN_ID
AND T40.LOAN_ID(+)=T21.LOAN_ID
ORDER BY
    INVESTOR_ID,
    LOAN_ID;
	