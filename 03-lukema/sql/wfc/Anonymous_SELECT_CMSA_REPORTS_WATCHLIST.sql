SET serveroutput ON format wrapped;
DECLARE
--
--
    in_nInvestorId NUMBER := 771;
in_nRepmonth NUMBER := 12;
in_nRepyear NUMBER := 2012;
out_dAsOfDt VARCHAR;
out_nStatusCode NUMBER;
out_cvGeneric PKG_CMSA_CommDefi.GenCurTyp;
out_cvGenericTot PKG_CMSA_CommDefi.GenCurTyp;
--
--
v_AsOfDate DATE;
v_dDistriDt DATE;
v_dLowerBndDt DATE;
v_PrevMonthDt DATE;
BEGIN
    out_nStatusCode := 0;
v_dDistriDt := PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_Fnc(in_nInvestorId ,in_nRepmonth,in_nRepyear);
v_AsOfDate := PKG_CMSA_Functions.Sp_Cmsa_Repdeterdt_lper_Fnc(in_nInvestorId,in_nRepmonth,
in_nRepyear);
v_PrevMonthDt := ADD_MONTHS(v_AsOfDate, -1) ;
v_dLowerBndDt := NVL(PKG_CMSA_Functions.SP_CMSA_RepDeterDt_FNC(in_nInvestorId,TO_NUMBER(TO_CHAR
(v_PrevMonthDt,'MM')),TO_NUMBER(TO_CHAR(v_PrevMonthDt,'YYYY')) ) + 1, v_PrevMonthDt +1) ;
IF v_dDistriDt-SYSDATE > 0 THEN
    out_dAsOfDt := PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(SYSDATE);
ELSE
    out_dAsOfDt := PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(v_dDistriDt);
END IF;
--
--
DBMS_OUTPUT.put_line('msg: ' || );
--
--
OPEN out_cvGeneric FOR
SELECT
    INV.TRANSACTION_ID,
    L.GROUP_ID,
    L.LOAN_ID,
    P.PROSPECTUS_LOAN_ID,
    DECODE(T1.PropertyCnt,1,T1.NAME,'Various') "NAME",
    T2.PROPERTY_CD,
    T2.CITY,
    T2.STATE,
    PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(PKG_CMSA_Functions.SP_CMSA_DeterDt_FNC(in_nInvestorId,
    PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(W.EFFECTIVE_DT))) "EFFECTIVE_DT", 
    PKG_CMSA_Functions.SP_CMSA_Endschbal_FNC(P.INVESTOR_ID,W.LOAN_ID,in_nRepmonth,in_nRepyear,NULL)
                                                           "END_SCH_BAL",
    PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T3.PAID_TO_DT) "PAID_TO_DT",
    PKG_CMSA_Functions.SP_CMSA_Datefrmt_FNC(PKG_CMSA_FUNCTIONS.SP_CMSA_MaturityDt_FNC(P.INVESTOR_ID
    ,W.LOAN_ID,in_nRepMonth,in_nRepYear)) "MATURITY_DT",
    PKG_CMSA_FUNCTIONS.SP_CMSA_CROSSCOLLNCFLN_FNC(W.LOAN_ID,1,'AN',in_nInvestorId,in_nRepmonth,
    in_nRepyear)                                                   "FISCAL_YR_DSCRNCF",
    PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T4.FISCAL_YR_END_YRMM) "FISCAL_YR_END_YRMM",
    DECODE(greatest(NVL(T4.FISCAL_YR_END_YRMM,SYSDATE),NVL(T5.BEGIN_YRMM,SYSDATE)),
    T4.FISCAL_YR_END_YRMM,NULL,PKG_CMSA_FUNCTIONS.SP_CMSA_CROSSCOLLNCFLN_FNC(W.LOAN_ID,0,'YTD',
    in_nInvestorId,in_nRepmonth,in_nRepyear)) "MOST_DSCRNCF",
    to_number(DECODE(greatest(NVL(T4.FISCAL_YR_END_YRMM,SYSDATE),NVL(T5.BEGIN_YRMM,SYSDATE)),
    T4.FISCAL_YR_END_YRMM,NULL, PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T5.BEGIN_YRMM)))
    "BEGIN_YRMM",
    to_number(DECODE(greatest(NVL(T4.FISCAL_YR_END_YRMM,SYSDATE),NVL(T5.BEGIN_YRMM,SYSDATE)),
    T4.FISCAL_YR_END_YRMM,NULL, PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T5.END_YRMM))) "END_YRMM",
    Sp_Cmsa_Loan_Refcd_Criteria(W.LOAN_ID,v_AsOfDate)                                  "REFCD_CRITERIA"
    ,
    W.COMMENTS
FROM
    (
        SELECT
            LP.LOAN_ID,
            NAME,
            propertyCnt
        FROM
            T_CMSA_PROPERTY P,
            T_CMSA_LOAN_PROPERTY LP,
            (
                SELECT
                    COUNT(PROPERTY_ID) PropertyCnt,
                    loan_id
                FROM
                    t_cmsa_loan_property
                WHERE
                    PROPERTY_ID NOT IN
                    (
                        SELECT
                            PROPERTY_ID
                        FROM
                            T_CMSA_PROPERTY_SWAP
                        WHERE
                            EFFECTIVE_DT<=v_AsOfDate)
                GROUP BY
                    loan_id
                HAVING
                    COUNT(property_id)=1) Q1
        WHERE
            P.PROPERTY_ID=LP.PROPERTY_ID
        AND P.PROPERTY_ID NOT IN
            (
                SELECT
                    PROPERTY_ID
                FROM
                    T_CMSA_PROPERTY_SWAP
                WHERE
                    EFFECTIVE_DT<=v_AsOfDate)
        AND LP.LOAN_ID=Q1.LOAN_ID) T1,
    (
        SELECT
            DECODE(CityCnt,1,CITY,'Various')   CITY,
            DECODE(StateCnt,1,STATE,'XX')      STATE,
            DECODE(PropCnt,1,PROPERTY_CD,'XX') PROPERTY_CD,
            LP.LOAN_ID
        FROM
            T_CMSA_LOAN_PROPERTY LP,
            T_CMSA_PROPERTY P,
            (
                SELECT
                    COUNT(DISTINCT CITY)        CityCnt,
                    COUNT(DISTINCT STATE)       StateCnt,
                    COUNT(DISTINCT PROPERTY_CD) PropCnt,
                    LOAN_ID
                FROM
                    T_CMSA_PROPERTY P,
                    T_CMSA_LOAN_PROPERTY LP
                WHERE
                    P.PROPERTY_ID=LP.PROPERTY_ID
                AND LP.PROPERTY_ID NOT IN
                    (
                        SELECT
                            PROPERTY_ID
                        FROM
                            T_CMSA_PROPERTY_SWAP
                        WHERE
                            EFFECTIVE_DT<=v_AsOfDate)
                GROUP BY
                    LP.LOAN_ID ) Q2
        WHERE
            LP.LOAN_ID=Q2.LOAN_ID
        AND LP.PROPERTY_ID= P.PROPERTY_ID
        AND LP.PROPERTY_ID NOT IN
            (
                SELECT
                    PROPERTY_ID
                FROM
                    T_CMSA_PROPERTY_SWAP
                WHERE
                    EFFECTIVE_DT<=v_AsOfDate )
        GROUP BY
            LP.LOAN_ID,
            DECODE(CityCnt,1,CITY,'Various'),
            DECODE(StateCnt,1,STATE,'XX'),
            DECODE(PropCnt,1,PROPERTY_CD,'XX')) T2,
    (
        SELECT
            LF.LOAN_ID,
            PAID_TO_DT
        FROM
            T_CMSA_DAILY_LOAN_FEED LF,
            (
                SELECT
                    LF.LOAN_ID,
                    MAX(Feed_Dt) Feed_Dt
                FROM
                    T_CMSA_DAILY_LOAN_FEED LF,
                    T_CMSA_POOL P
                WHERE
                    LF.LOAN_ID=P.LOAN_ID
                AND Feed_Dt BETWEEN v_dLowerBndDt AND v_AsOfDate
                AND P.INVESTOR_ID=in_nInvestorId
                GROUP BY
                    LF.LOAN_ID) Q
        WHERE
            LF.LOAN_ID=Q.LOAN_ID
        AND LF.FEED_DT=Q.FEED_DT) T3,
    (
        SELECT
            LP.LOAN_ID,
            DECODE(COUNT(DISTINCT FP.END_YRMM),1,MAX(FP.END_YRMM),NULL) "FISCAL_YR_END_YRMM"
        FROM
            T_CMSA_FINANCIAL_SUMMARY FS,
            T_CMSA_FINANCIAL_PERIOD FP,
            T_CMSA_LOAN_PROPERTY LP,
            T_CMSA_POOL P
        WHERE
            P.Investor_Id = in_nInvestorId
        AND P.Loan_Id = LP.Loan_Id
        AND FS.PROPERTY_ID=FP.PROPERTY_ID
        AND FS.DATA_TYPE=FP.DATA_TYPE
        AND FS.STATEMENT_PERIOD=FP.STATEMENT_PERIOD
        AND FS.PROPERTY_ID=LP.PROPERTY_ID
        AND FP.DATA_TYPE='AN'
        AND FP.PREV_YR_NUM=1
        AND LP.PROPERTY_ID NOT IN
            (
                SELECT
                    PROPERTY_ID
                FROM
                    T_CMSA_PROPERTY_SWAP
                WHERE
                    EFFECTIVE_DT <=v_AsOfDate)
        GROUP BY
            LP.LOAN_ID) T4,
    (
        SELECT
            LP.LOAN_ID,
            DECODE(MIN(DECODE(FP.BEGIN_YRMM,NULL,0)),0,NULL,DECODE(COUNT(DISTINCT FP.BEGIN_YRMM),1,
            MAX(FP.BEGIN_YRMM),NULL)) "BEGIN_YRMM",
            DECODE(MIN(DECODE(FP.END_YRMM,NULL,0)),0,NULL,DECODE(COUNT(DISTINCT FP.END_YRMM),1,MAX
            (FP.END_YRMM),NULL)) "END_YRMM"
        FROM
            T_CMSA_FINANCIAL_SUMMARY FS,
            T_CMSA_FINANCIAL_PERIOD FP,
            T_CMSA_LOAN_PROPERTY LP,
            (
                SELECT
                    LP.LOAN_ID,
                    MAX(FP.END_YRMM) END_YRMM
                FROM
                    T_CMSA_FINANCIAL_PERIOD FP,
                    T_CMSA_LOAN_PROPERTY LP,
                    T_CMSA_POOL P
                WHERE
                    P.Investor_Id = in_nInvestorId
                AND P.Loan_Id = LP.Loan_Id
                AND FP.PROPERTY_ID=LP.PROPERTY_ID
                AND FP.DATA_TYPE IN ('YTD',
                                     'TR')
                GROUP BY
                    LP.LOAN_ID) Q1
        WHERE
            FS.PROPERTY_ID=FP.PROPERTY_ID
        AND FS.DATA_TYPE=FP.DATA_TYPE
        AND FS.STATEMENT_PERIOD=FP.STATEMENT_PERIOD
        AND FS.PROPERTY_ID=LP.PROPERTY_ID
        AND FP.DATA_TYPE IN ('YTD',
                             'TR')
        AND LP.PROPERTY_ID NOT IN
            (
                SELECT
                    PROPERTY_ID
                FROM
                    T_CMSA_PROPERTY_SWAP
                WHERE
                    EFFECTIVE_DT <=v_AsOfDate)
        AND LP.LOAN_ID=Q1.LOAN_ID
        AND FP.END_YRMM=Q1.END_YRMM
        GROUP BY
            LP.LOAN_ID) T5,
    T_CMSA_WATCHLIST W,
    T_CMSA_POOL P,
    T_CMSA_LOAN L,
    T_CMSA_INVESTOR INV
WHERE
    W.LOAN_ID=P.LOAN_ID
AND L.LOAN_ID=P.LOAN_ID
AND INV.INVESTOR_ID=P.INVESTOR_ID
AND T1.LOAN_ID(+)=W.LOAN_ID
AND T2.LOAN_ID(+)=W.LOAN_ID
AND T3.LOAN_ID(+)=W.LOAN_ID
AND T4.LOAN_ID(+)=W.LOAN_ID
AND T5.LOAN_ID(+)=W.LOAN_ID
AND W.LOAN_ID NOT IN
    (
        SELECT
            LOAN_ID
        FROM
            V_CMSA_PAYOFF
        WHERE
            LOAN_ID = P.LOAN_ID)
AND NOT EXISTS
    (
        SELECT
            'X'
        FROM
            T_CMSA_LOAN_SWAP
        WHERE
            LOAN_ID=P.LOAN_ID
        AND EFFECTIVE_DT<=v_AsOfDate )
AND NOT EXISTS
    (
        SELECT
            'X'
        FROM
            T_CMSA_SPECIAL_SERVICING
        WHERE
            LOAN_ID=P.LOAN_ID
        AND TRANSFER_DT<v_AsOfDate
        AND (
                RETURN_DT IS NULL
            OR  RETURN_DT>v_AsOfDate) )
AND W.EFFECTIVE_DT<=v_AsOfDate
AND (
        W.RELEASE_DT IS NULL )
AND W.LOAN_ID IN
    (
        SELECT
            LOAN_ID
        FROM
            T_CMSA_LOAN_WATCHLIST
        WHERE
            EFFECTIVE_DT<=v_AsOfDate
        AND (
                CRITERIA_REL_DT IS NULL ) )
AND W.LOAN_ID NOT IN
    (
        SELECT
            PL.LOAN_ID
        FROM
            T_CMSA_PERIODIC_LOAN PL,
            T_CMSA_POOL POOL
        WHERE
            PL.LOAN_ID = POOL.LOAN_ID
        AND INVESTOR_ID = in_nInvestorId
        AND PERIODIC_MONTH = in_nRepmonth
        AND PERIODIC_YEAR = in_nRepYear
        AND DEFEASANCE_CD = 'F' )
AND P.INVESTOR_ID=in_nInvestorId
AND Sp_Cmsa_Loan_Refcd_Criteria(W.LOAN_ID,v_AsOfDate) IS NOT NULL
ORDER BY
    END_SCH_BAL ASC,
    P.PROSPECTUS_LOAN_ID,
    W.EFFECTIVE_DT;
OPEN out_cvGenericTot FOR
SELECT
    SUM(PKG_CMSA_Functions.SP_CMSA_Endschbal_FNC(P.INVESTOR_ID,W.LOAN_ID,in_nRepmonth,in_nRepyear,
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
        AND INVESTOR_ID = in_nInvestorId
        AND PERIODIC_MONTH = in_nRepmonth
        AND PERIODIC_YEAR = in_nRepYear
        AND DEFEASANCE_CD = 'F' )
AND NOT EXISTS
    (
        SELECT
            'X'
        FROM
            T_CMSA_LOAN_SWAP
        WHERE
            LOAN_ID=P.LOAN_ID
        AND EFFECTIVE_DT<=v_AsOfDate )
AND NOT EXISTS
    (
        SELECT
            'X'
        FROM
            T_CMSA_SPECIAL_SERVICING
        WHERE
            LOAN_ID=P.LOAN_ID
        AND TRANSFER_DT<v_AsOfDate
        AND (
                RETURN_DT IS NULL
            OR  RETURN_DT>v_AsOfDate) )
AND W.EFFECTIVE_DT<=v_AsOfDate
AND (
        W.RELEASE_DT IS NULL
    OR  W.RELEASE_DT>v_AsOfDate)
AND W.LOAN_ID IN
    (
        SELECT
            LOAN_ID
        FROM
            T_CMSA_LOAN_WATCHLIST
        WHERE
            EFFECTIVE_DT<=v_AsOfDate
        AND CRITERIA_REL_DT IS NULL )
AND P.INVESTOR_ID=in_nInvestorId
AND Sp_Cmsa_Loan_Refcd_Criteria(W.LOAN_ID,v_AsOfDate) IS NOT NULL ;
EXCEPTION
WHEN OTHERS THEN
    out_nStatusCode := 1;
PKG_CMSA_ErrorHandling.SP_CMSA_LogErrMsg(SQLERRM);
PKG_CMSA_ErrorHandling.SP_CMSA_GetErrMsg(out_cvGeneric);
PKG_CMSA_ErrorHandling.SP_CMSA_GetErrMsg(out_cvGenericTot);
END SP_CMSA_Reports_Watchlist;
 