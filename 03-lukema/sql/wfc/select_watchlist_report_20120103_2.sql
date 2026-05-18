SELECT
    TRANSACTION_ID,
    LOAN_ID,
    PROSPECTUS_LOAN_ID,
    EFFECTIVE_DT,
    REFCD_CRITERIA
FROM
    (
        SELECT
            INV.TRANSACTION_ID,
            L.GROUP_ID,
            L.LOAN_ID,
            P.PROSPECTUS_LOAN_ID,
            DECODE(T1.PropertyCnt,1,T1.NAME,'Various') "NAME",
            T2.PROPERTY_CD,
            T2.CITY,
            T2.STATE,
            PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(PKG_CMSA_Functions.SP_CMSA_DeterDt_FNC(771,
            PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(W.EFFECTIVE_DT))) "EFFECTIVE_DT",
            PKG_CMSA_Functions.SP_CMSA_Endschbal_FNC(P.INVESTOR_ID,W.LOAN_ID,12,2012,NULL)
                                                                   "END_SCH_BAL",
            PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T3.PAID_TO_DT) "PAID_TO_DT",
            PKG_CMSA_Functions.SP_CMSA_Datefrmt_FNC(PKG_CMSA_FUNCTIONS.SP_CMSA_MaturityDt_FNC
            (P.INVESTOR_ID ,W.LOAN_ID,12,2012)) "MATURITY_DT",
            PKG_CMSA_FUNCTIONS.SP_CMSA_CROSSCOLLNCFLN_FNC(W.LOAN_ID,1,'AN',771,12, 2012)
                                                                           "FISCAL_YR_DSCRNCF",
            PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T4.FISCAL_YR_END_YRMM) "FISCAL_YR_END_YRMM",
            DECODE(greatest(NVL(T4.FISCAL_YR_END_YRMM,SYSDATE),NVL(T5.BEGIN_YRMM,SYSDATE)),
            T4.FISCAL_YR_END_YRMM,NULL,PKG_CMSA_FUNCTIONS.SP_CMSA_CROSSCOLLNCFLN_FNC(W.LOAN_ID,0,
            'YTD', 771,12,2012)) "MOST_DSCRNCF",
            to_number(DECODE(greatest(NVL(T4.FISCAL_YR_END_YRMM,SYSDATE),NVL(T5.BEGIN_YRMM,SYSDATE)
            ), T4.FISCAL_YR_END_YRMM,NULL, PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T5.BEGIN_YRMM)))
            "BEGIN_YRMM",
            to_number(DECODE(greatest(NVL(T4.FISCAL_YR_END_YRMM,SYSDATE),NVL(T5.BEGIN_YRMM,SYSDATE)
            ), T4.FISCAL_YR_END_YRMM,NULL, PKG_CMSA_Functions.Sp_Cmsa_Datefrmt_Fnc(T5.END_YRMM)))
                                                               "END_YRMM",
            Sp_Cmsa_Loan_Refcd_Criteria(W.LOAN_ID,'11-DEC-12') "REFCD_CRITERIA" ,
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
                                    EFFECTIVE_DT<='11-DEC-12')
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
                            EFFECTIVE_DT<='11-DEC-12')
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
                                    EFFECTIVE_DT<='11-DEC-12')
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
                            EFFECTIVE_DT<='11-DEC-12' )
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
                        AND Feed_Dt BETWEEN '14-NOV-12' AND '11-DEC-12'
                        AND P.INVESTOR_ID=771
                        GROUP BY
                            LF.LOAN_ID) Q
                WHERE
                    LF.LOAN_ID=Q.LOAN_ID
                AND LF.FEED_DT=Q.FEED_DT) T3,
            (
                SELECT
                    LP.LOAN_ID,
                    DECODE(COUNT(DISTINCT FP.END_YRMM),1,MAX(FP.END_YRMM),NULL)
                    "FISCAL_YR_END_YRMM"
                FROM
                    T_CMSA_FINANCIAL_SUMMARY FS,
                    T_CMSA_FINANCIAL_PERIOD FP,
                    T_CMSA_LOAN_PROPERTY LP,
                    T_CMSA_POOL P
                WHERE
                    P.Investor_Id = 771
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
                            EFFECTIVE_DT <='11-DEC-12')
                GROUP BY
                    LP.LOAN_ID) T4,
            (
                SELECT
                    LP.LOAN_ID,
                    DECODE(MIN(DECODE(FP.BEGIN_YRMM,NULL,0)),0,NULL,DECODE(COUNT(DISTINCT
                    FP.BEGIN_YRMM),1, MAX(FP.BEGIN_YRMM),NULL)) "BEGIN_YRMM",
                    DECODE(MIN(DECODE(FP.END_YRMM,NULL,0)),0,NULL,DECODE(COUNT(DISTINCT FP.END_YRMM
                    ),1,MAX (FP.END_YRMM),NULL)) "END_YRMM"
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
                            P.Investor_Id = 771
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
                            EFFECTIVE_DT <='11-DEC-12')
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
                AND EFFECTIVE_DT<='11-DEC-12' )
        AND NOT EXISTS
            (
                SELECT
                    'X'
                FROM
                    T_CMSA_SPECIAL_SERVICING
                WHERE
                    LOAN_ID=P.LOAN_ID
                AND TRANSFER_DT<'11-DEC-12'
                AND (
                        RETURN_DT IS NULL
                    OR  RETURN_DT>'11-DEC-12') )
        AND W.EFFECTIVE_DT<='11-DEC-12'
        AND (
                W.RELEASE_DT IS NULL )
        AND W.LOAN_ID IN
            (
                SELECT
                    LOAN_ID
                FROM
                    T_CMSA_LOAN_WATCHLIST
                WHERE
                    EFFECTIVE_DT<='11-DEC-12'
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
                AND INVESTOR_ID = 771
                AND PERIODIC_MONTH = 12
                AND PERIODIC_YEAR = 2012
                AND DEFEASANCE_CD = 'F' )
        AND P.INVESTOR_ID=771
        AND Sp_Cmsa_Loan_Refcd_Criteria(W.LOAN_ID,'11-DEC-12') IS NOT NULL)
ORDER BY
    2;
	
 