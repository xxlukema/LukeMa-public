SET serveroutput ON format wrapped;
DECLARE
  in_nInvestorId NUMBER;
  in_nMonth      NUMBER;
  in_nYear       NUMBER;
  --
  out_dDistriDt DATE;
  out_dDeterminDt DATE ;
  out_cInvestorName VARCHAR2;
  out_cSeries       VARCHAR2 ;
  out_nStatusCode   NUMBER;
  out_cvPrincipal Pkg_Cmsa_Commdefi.GenCurTyp;
  out_cvInterest Pkg_Cmsa_Commdefi.GenCurTyp;
  out_cvSerFee Pkg_Cmsa_Commdefi.GenCurTyp;
  out_cvAdvances Pkg_Cmsa_Commdefi.GenCurTyp;
  out_cvMiscellaneous Pkg_Cmsa_Commdefi.GenCurTyp;
  --
  v_nSchPrincipal     NUMBER ;
  v_nUnSchPrincipal   NUMBER ;
  v_nPrincipalToRemit NUMBER ;
  v_NagativeAmort     NUMBER ;
  v_nSchInterest      NUMBER ;
  v_nLessAdjNagAmort  NUMBER ;
  v_nLessTotServFee   NUMBER ;
  v_nMstrServFee      NUMBER ;
  v_nLessIntResAmt    NUMBER ;
  v_nPayOffCurtail    NUMBER ;
  v_nSplStandByFee    NUMBER ;
  v_nSplServFee       NUMBER ;
  v_nPrimaryServFee   NUMBER ;
  v_nTrusteeFee       NUMBER ;
  v_nWorkOutFee       NUMBER ;
  v_nTotalAdminFee    NUMBER ;
  v_nLessTotSerFee    NUMBER ;
  v_dDistriDate DATE ;
  v_dDeterminDt DATE ;
  v_dRemitDt DATE ;
  v_dLowerBndDt DATE ;
  v_dPrevMonthDt DATE ;
  v_nStatusCode  NUMBER ;
  v_cvSerFee     VARCHAR2(32767);
  v_Fee_List     VARCHAR2(1000);
  v_Adm_Fee_List VARCHAR2(1000);
  v_Fee_Q1       VARCHAR2(2000);
  v_Fee_Q2       VARCHAR2(1000);
  v_Fee_Q        VARCHAR2(2000);
  v_Fee_Qa       VARCHAR2(2000);
  v_Fee_Q1a      VARCHAR2(2000);
  CURSOR cvSerFeeList
  IS
    SELECT Fee_Type,
      REPLACE(SUBSTR(Description,1,30),' ','_') Description
    FROM T_CMSA_FEE_TYPE
    WHERE Fee_Type NOT IN (1,4,5,8,2,3,6,7,9)
    ORDER BY Fee_Type;
  CURSOR cvAdmSerFeeList
  IS
    SELECT Fee_Type,
      REPLACE(SUBSTR(Description,1,30),' ','_') Description
    FROM T_CMSA_FEE_TYPE
    WHERE Fee_Type NOT IN (1,4,5,8,2,3,6,7,9)
    AND ADMIN_FEE_YN    ='Y'
    ORDER BY Fee_Type;
BEGIN
  in_nInvestorId := 506;
  in_nMonth      := 8;
  in_nYear       := 2012;
  --
  BEGIN
    out_nStatusCode := 0 ;
    BEGIN
      SELECT Report_Deter_Dt,
        Report_Distri_Dt,
        Trustee_Remit_Dt
      INTO v_dDeterminDt,
        v_dDistriDate,
        v_dRemitDt
      FROM T_CMSA_DISTRIBUTION_CALENDAR
      WHERE Investor_Id   = in_nInvestorId
      AND Reporting_Month = in_nMonth
      AND Reporting_Year  = in_nYear ;
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      out_nStatusCode := 1; -- Send fail status back.
      -- log error message into temp table
      Pkg_Cmsa_Errorhandling.SP_CMSA_LogErrMsg('Determination date is not found in calendar for investor ' || TO_CHAR(in_nInvestorId) || ' in the month of ' || TO_CHAR(in_nMonth) || '/' || TO_CHAR(in_nYear) || '.');
      -- retrieve all errors in cusror variable
      Pkg_Cmsa_Errorhandling.SP_CMSA_GetErrMsg(out_cvAdvances);
      RETURN ;
    END ;
    out_dDistriDt   := v_dDistriDate ; -- send back this Distribution Date.
    v_dPrevMonthDt  := ADD_MONTHS(v_dDeterminDt,                                                                                                                        -1) ;
    v_dLowerBndDt   := NVL(Pkg_Cmsa_Functions.SP_CMSA_RepDeterDt_FNC(in_nInvestorId,TO_NUMBER(TO_CHAR(v_dPrevMonthDt,'MM')),TO_NUMBER(TO_CHAR(v_dPrevMonthDt,'YYYY')) ) + 1, v_dPrevMonthDt +1) ;
    out_dDeterminDt := v_dDeterminDt ; -- send back this Determination Date.
    -- fetch the Investor Name from T_CMSA_INVESTOR table.
    -- Query separates the Name and Series, which starts with the Year from the T_CMSA_INVESTOR.INVESTOR_NAME column.
    -- separation is taking place with '199' and '200' , which are the initial digits of the Century.
    SELECT SUBSTR( INVESTOR_NAME, 0, DECODE( INSTR( INVESTOR_NAME, '199' ) , 0, INSTR( INVESTOR_NAME, '20' )-1, INSTR( INVESTOR_NAME, '199' )-1 ) ),
      'Series '
      || DECODE( INSTR( INVESTOR_NAME, '199' ) , 0, SUBSTR( INVESTOR_NAME, INSTR( INVESTOR_NAME, '20' ) ),SUBSTR( INVESTOR_NAME, INSTR( INVESTOR_NAME, '199' )))
    INTO out_cInvestorName,
      out_cSeries
    FROM T_CMSA_INVESTOR
    WHERE INVESTOR_ID = in_nInvestorId ;
    OPEN out_cvPrincipal FOR SELECT LOAN_ID,
    PROSPECTUS_LOAN_ID,
    NAME ,
    PICONST ,
    BEGINSCHBAL ,
    v_nSchPrincipal ,
    V_NUNSCHPRINCIPAL ,
    LIQUI_PREPYMT_CD ,
    UNSCHPRINCIPALDT ,
    PRINCIPAL_ADJ_AMT ,
    REALIZED_LOSS ,
    (v_nSchPrincipal                              + V_NUNSCHPRINCIPAL + PRINCIPAL_ADJ_AMT ) V_NPRINCIPALTOREMIT ,
    DECODE(Neg_Amort_Flag,'Y',DECODE(SIGN(PICONST - v_nSchPrincipal - v_nSchInterest),1,(PICONST - v_nSchPrincipal - v_nSchInterest),0),0) V_NAGATIVEAMORT,
    ( BEGINSCHBAL                                 - v_nSchPrincipal - V_NUNSCHPRINCIPAL - PRINCIPAL_ADJ_AMT + DECODE(Neg_Amort_Flag,'Y',DECODE(SIGN(PICONST - v_nSchPrincipal - v_nSchInterest),1,(PICONST - v_nSchPrincipal - v_nSchInterest),0),0) ) - NVL(REALIZED_LOSS,0) ENDINGSCHPRINCIPAL FROM
    (SELECT Pool.Loan_Id LOAN_ID,
      Pool.PROSPECTUS_LOAN_ID PROSPECTUS_LOAN_ID,
      CompFeed.BORROWER_NAME NAME,
      L.Neg_Amort_Flag,
      NVL(Pkg_Cmsa_Functions.SP_CMSA_PIConst_FNC(in_nInvestorId, Pool.Loan_Id,in_nMonth, in_nYear),0) "PICONST",
      NVL(PrdcLoan.Begin_Sch_Bal,0) BEGINSCHBAL,
      NVL(Pkg_Cmsa_Functions.SP_CMSA_SchPrincipal_FNC(in_nInvestorId,Pool.Loan_Id,in_nMonth,in_nYear),0) v_nSchPrincipal,
      NVL(Pkg_Cmsa_Functions.SP_CMSA_SchInterest_FNC(in_nInvestorId,Pool.Loan_Id,in_nMonth,in_nYear),0) v_nSchInterest,
      NVL(DECODE(DlyLnFeed.Actual_Bal,0,(PrdcLoan.Begin_Sch_Bal - Pkg_Cmsa_Functions.SP_CMSA_SchPrincipal_FNC(in_nInvestorId,Pool.Loan_Id,in_nMonth,in_nYear)-NVL(LIQ.REALIZED_LOSS,0) ), DECODE(UnschDtFeed.UnschBal_Dt,NULL,0, UnschBalFeed.UnschBal-NVL(LIQ.REALIZED_LOSS,0) )),0) V_NUNSCHPRINCIPAL,
      NVL(LIQ.REALIZED_LOSS,0) REALIZED_LOSS,
      Liquidation.Liqui_Prepymt_Cd,
      UnschDtFeed.UnschBal_Dt UNSCHPRINCIPALDT,
      NVL(PrdcLoan.Principal_Adj_Amt,0) PRINCIPAL_ADJ_AMT
    FROM T_CMSA_POOL Pool,
      T_CMSA_LOAN L,
      (SELECT PL.Loan_Id,
        (Begin_Sch_Bal     * PERCENT_OWNED) Begin_Sch_Bal,
        (Principal_Adj_Amt * PERCENT_OWNED) Principal_Adj_Amt
      FROM T_CMSA_PERIODIC_LOAN PL,
        T_CMSA_POOL P
      WHERE INVESTOR_ID  = in_nInvestorId
      AND P.LOAN_ID      = PL.LOAN_ID
      AND Periodic_Month = in_nMonth
      AND Periodic_Year  = in_nYear
      ) PrdcLoan,
      (SELECT LQ.Loan_ID,
        LQ.Liqui_Prepymt_Cd
      FROM T_CMSA_LIQUIDATION LQ,
        (SELECT Loan_Id,
          MAX(LIQUI_PREPYMT_DT) LIQUI_PREPYMT_DT
        FROM T_CMSA_LIQUIDATION
        WHERE LIQUI_PREPYMT_DT BETWEEN v_dLowerBndDt AND v_dDeterminDt
        GROUP BY LOAN_ID
        ) TMP
      WHERE LQ.Loan_Id        = TMP.Loan_Id
      AND LQ.LIQUI_PREPYMT_DT = TMP.LIQUI_PREPYMT_DT
      ) Liquidation,
      (SELECT DF.Loan_Id,
        DF.Actual_Bal
      FROM T_CMSA_DAILY_LOAN_FEED DF,
        (SELECT F.LOAN_ID,
          MAX(F.FEED_DT) FEED_DT
        FROM T_CMSA_DAILY_LOAN_FEED F,
          T_CMSA_POOL P
        WHERE P.INVESTOR_ID = in_nInvestorId
        AND P.Loan_Id       = F.Loan_Id
        AND F.FEED_DT BETWEEN v_dLowerBndDt AND v_dDeterminDt
        GROUP BY F.LOAN_ID
        ) Q
      WHERE DF.Loan_Id = Q.Loan_Id
      AND DF.Feed_Dt   = Q.Feed_Dt
      ) DlyLnFeed,
      /* (SELECT F.LOAN_ID, MAX(F.FEED_DT) UnschBal_Dt FROM T_CMSA_DAILY_LOAN_FEED F, T_CMSA_POOL P
      WHERE P.INVESTOR_ID = in_nInvestorId AND P.Loan_Id = F.Loan_Id AND
      F.FEED_DT BETWEEN v_dLowerBndDt AND v_dDeterminDt AND Unsch_Principal_Amt > 0
      GROUP BY F.LOAN_ID) UnschDtFeed,*/
      (
      SELECT P.LOAN_ID,
        Pkg_Cmsa_Reports_Remittance.SP_CMSA_UNSCPRIDT_FNC(in_nInvestorId,P.LOAN_ID,v_dLowerBndDt,v_dDeterminDt ) AS UnschBal_Dt
      FROM T_CMSA_POOL P
      WHERE INVESTOR_ID=in_nInvestorId
      ) UnschDtFeed,
      (SELECT LF.LOAN_ID,
        SUM(LF.Unsch_Principal_Amt * PERCENT_OWNED) UnschBal
      FROM T_CMSA_DAILY_LOAN_FEED LF,
        T_CMSA_POOL P
      WHERE P.INVESTOR_ID = in_nInvestorId
      AND P.Loan_Id       = LF.Loan_Id
      AND LF.FEED_DT BETWEEN v_dLowerBndDt AND v_dDeterminDt
      GROUP BY LF.LOAN_ID
      ) UnschBalFeed,
      (SELECT Feed.LOAN_ID,
        BORROWER_NAME
      FROM T_CMSA_COMPARISON_FEED Feed,
        T_CMSA_POOL Pool
      WHERE POOL.INVESTOR_ID = in_nInvestorId
      AND Pool.Loan_Id       = Feed.Loan_Id
      ) CompFeed,
      (SELECT LQ.LOAN_ID,
        LQ.REALIZED_LOSS*P.PERCENT_OWNED REALIZED_LOSS
      FROM T_CMSA_LIQUIDATION LQ,
        T_CMSA_POOL P,
        (SELECT LOAN_ID,
          MAX(LIQUI_PREPYMT_DT) LIQUI_PREPYMT_DT
        FROM T_CMSA_LIQUIDATION
        WHERE LIQUI_PREPYMT_DT BETWEEN v_dLowerBndDt AND v_dDeterminDt
        OR LIQUI_PREPYMT_DT IS NULL
        GROUP BY LOAN_ID
        ) Q
      WHERE P.Investor_Id     = in_nInvestorId
      AND LQ.LOAN_ID          =P.LOAN_ID
      AND LQ.LOAN_ID          =Q.LOAN_ID
      AND (LQ.LIQUI_PREPYMT_DT=Q.LIQUI_PREPYMT_DT
      OR LQ.LIQUI_PREPYMT_DT IS NULL)
      ) LIQ
    WHERE Pool.Loan_Id    = L.Loan_Id
    AND Pool.Loan_Id      = PrdcLoan.Loan_Id(+)
    AND Pool.Loan_Id      = DlyLnFeed.Loan_Id(+)
    AND Pool.Loan_Id      = UnschDtFeed.Loan_Id(+)
    AND Pool.Loan_Id      = UnschBalFeed.Loan_Id(+)
    AND Pool.Loan_Id      = Liquidation.Loan_Id(+)
    AND Pool.Loan_Id      = Liq.Loan_Id(+)
    AND Pool.Loan_Id      = CompFeed.Loan_Id(+)
    AND Pool.Loan_Id NOT IN
      (SELECT pl.Loan_Id
      FROM T_CMSA_PERIODIC_LOAN pl,
        t_cmsa_pool p
      WHERE pl.loan_id   = p.loan_id
      AND p.investor_id  = in_nInvestorId
      AND PERIODIC_MONTH = in_nMonth
      AND PERIODIC_YEAR  = in_nYear
      AND BEGIN_SCH_BAL  = 0
      )
    AND Pool.Loan_Id NOT IN
      (SELECT po.Loan_Id
      FROM V_CMSA_PAYOFF po,
        t_cmsa_pool p
      WHERE po.loan_id              = p.loan_id
      AND p.investor_id             = in_nInvestorId
      AND ADD_MONTHS(Payoff_Date,1) < v_dDeterminDt
      UNION
      SELECT LOAN_ID FROM T_CMSA_LOAN_SWAP WHERE EFFECTIVE_DT <= v_dDeterminDt
      )
    AND Pool.Investor_Id = in_nInvestorId
    ORDER BY Pool.Loan_Id ASC
    );
    v_Fee_List    :='MASTER_SERVICE_FEE, PAYOFF_CURTAILMENT_SHORTFALL, SPECIAL_STANDBY_FEE, SPECIAL_SERVICING_FEE, WORKOUT_OTHER_FEE, PRIMARY_SERVICER_FEE, TRUSTEE_FEE';
    v_Fee_Q1      :=', ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC('||in_nInvestorId||',Pool.Loan_Id, ' ;
    v_Fee_Q2      :=', '||in_nMonth||', '||in_nYear||'),0),2) ';
    v_Fee_Q       :='';
    v_Adm_Fee_List:='';
    v_Fee_Qa      :='';
    v_Fee_Q1a     :='+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC('||in_nInvestorId||',Pool.Loan_Id, ' ;
    FOR Fee_List  IN cvSerFeeList
    LOOP
      v_Fee_List:=v_Fee_List ||', '||Fee_List.Description ;
      v_Fee_Q   :=v_Fee_Q ||v_Fee_Q1 || Fee_List.Fee_Type ||v_Fee_Q2 || Fee_List.Description;
    END LOOP;
    FOR AdmFee_List IN cvAdmSerFeeList
    LOOP
      v_Adm_Fee_List:= v_Adm_Fee_List||'+'||AdmFee_List.Description ;
      v_Fee_Qa      :=v_Fee_Qa ||v_Fee_Q1a || AdmFee_List.Fee_Type ||v_Fee_Q2 ;
    END LOOP;
    OPEN out_cvInterest FOR
    'SELECT LOAN_ID,    







PROSPECTUS_LOAN_ID,    







BORROWER_NAME,    







INTERESTRATE,    







V_NSCHINTEREST,    







NETRATE_UNSCEDULED_INTREST,    







INTREST_ADJ,    







V_NLESSADJNAGAMORT,    







TOTALFEE V_NLESSTOTSERFEE,    







V_NLESSINTRESAMT,







--    (V_NSCHINTEREST + NETRATE_UNSCEDULED_INTREST + INTREST_ADJ - V_NLESSADJNAGAMORT - TOTALFEE + V_NLESSINTRESAMT + PREPYMT_PENALTY_YLD_MAINT ) NETINTERESTREMITTANCE ,







-- June Start Build







--    (V_NSCHINTEREST + NETRATE_UNSCEDULED_INTREST + INTREST_ADJ - V_NLESSADJNAGAMORT - TOTALFEE + V_NLESSINTRESAMT  ) NETINTERESTREMITTANCE ,    







(V_NSCHINTEREST + INTREST_ADJ - V_NLESSADJNAGAMORT - TOTALFEE + V_NLESSINTRESAMT) NETINTERESTREMITTANCE ,







-- June Build End    







--cmsa 5.0 by Wilson Paulraj     







(PREPYMT_PENALTY_YLD_MAINT + YLD_MAINT ) PREPYMT_PENALTY_YLD_MAINT,    







YLD_MAINT,    







EXIT_FEE,    







DEF_INTEREST_TRUST







FROM  ( SELECT Pool.Loan_Id LOAN_ID,    







Pool.PROSPECTUS_LOAN_ID PROSPECTUS_LOAN_ID,    







CompFeed.Borrower_Name BORROWER_NAME ,    







NVL(Pkg_Cmsa_Functions.SP_CMSA_LastNoteRate_FNC(:in_nInvestorId, Pool.Loan_Id,:in_nMonth,:in_nYear),0) * 100 "INTERESTRATE",    







NVL(Pkg_Cmsa_Functions.SP_CMSA_SchInterest_FNC(:in_nInvestorId, Pool.Loan_Id,:in_nMonth,:in_nYear),0) V_NSCHINTEREST,    







NVL(TempTab.UNSCH_INTEREST_AMT,0) NETRATE_UNSCEDULED_INTREST,    







NVL(TempTab.INTEREST_ADJ_AMT,0) INTREST_ADJ,    







NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_NegativeAmort_FNC(:in_nInvestorId,Pool.Loan_Id,:in_nMonth,:in_nYear),0) V_NLESSADJNAGAMORT,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id,1,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL( TempTab.v_nPayOffCurtail * PERCENT_OWNED,0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 4,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 5,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.Sp_Cmsa_Workoutfee_Fnc(:in_nInvestorId,Pool.Loan_Id,8,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 2,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 3,:in_nMonth,:in_nYear),0),2)    







'
    || v_Fee_Qa ||
    ' TOTALFEE,    







Pkg_Cmsa_Reports_Remittance.SP_CMSA_InterestReserve_FNC(:in_nInvestorId,Pool.Loan_Id,:in_nMonth,:in_nYear,CompFeed.INTEREST_BASIS_CD) V_NLESSINTRESAMT ,    







NVL(TempTab1.PREPYMT_PENALTY_YLD_MAINT,0) PREPYMT_PENALTY_YLD_MAINT,    







--CMSA 5.0 By wilson paulraj        







NVL(TempTab1.YLD_MAINT,0) YLD_MAINT,        







NVL(TempTab1.EXIT_FEE,0) EXIT_FEE,        







NVL(TempTab1.DEF_INTEREST_TRUST,0) DEF_INTEREST_TRUST    







FROM T_CMSA_POOL Pool,        







T_CMSA_COMPARISON_FEED CompFeed,        







(SELECT SplServ.Loan_Id Loan_Id FROM T_CMSA_SPECIAL_SERVICING SplServ WHERE TRANSFER_DT <= :v_dDeterminDt AND (RETURN_DT IS NULL OR RETURN_DT > :v_dDeterminDt) )SplService,        







(SELECT Pool.Loan_Id,SUM(v_nPayOffCurtail) v_nPayOffCurtail,        







MAX(PrdcLoan.Unsch_Interest_Amt) * MAX(Pool.PERCENT_OWNED) Unsch_Interest_Amt,        







MAX(PrdcLoan.Interest_Adj_Amt) * MAX(Pool.PERCENT_OWNED) Interest_Adj_Amt        







FROM T_CMSA_PERIODIC_LOAN PrdcLoan,            







T_CMSA_POOL Pool,            







(SELECT Liqui.Loan_Id,            







0 v_nPayOffCurtail        







--(NVL(MAX(PrdcLoan.UNSCH_INTEREST_AMT) * MAX(Pool.PERCENT_OWNED),0) + NVL(MAX(PrdcLoan.INTEREST_ADJ_AMT) * MAX(Pool.PERCENT_OWNED),0) ) * NVL(MAX(Pkg_Cmsa_Reports_Remittance.SP_CMSA_TotAdminFeeRate_FNC(in_nInvestorId, Liqui.Loan_Id )),0)/ NVL(MAX(Pkg_Cmsa_Functions.SP_CMSA_LastNoteRate_FNC(in_nInvestorId,Liqui.Loan_Id,in_nMonth,in_nYear)),1) v_nPayOffCurtail            







FROM T_CMSA_LIQUIDATION Liqui,T_CMSA_PERIODIC_LOAN PrdcLoan, T_CMSA_POOL Pool            







WHERE Liqui.Loan_Id = PrdcLoan.Loan_Id            







AND Liqui.LIQUI_PREPYMT_DT BETWEEN :v_dLowerBndDt AND :out_dDeterminDt            







AND Pool.Investor_Id = :in_nInvestorId AND Pool.Loan_Id = Liqui.Loan_Id            







AND PrdcLoan.Periodic_Month = :in_nMonth AND PrdcLoan.Periodic_Year = :in_nYear            







GROUP BY Liqui.Loan_Id ) Q1       







WHERE  Pool.Loan_Id = PrdcLoan.Loan_Id       







AND Pool.Loan_Id = Q1.Loan_Id(+)       







AND Pool.INVESTOR_ID = :in_nInvestorId       







AND PrdcLoan.Periodic_Month = :in_nMonth AND PrdcLoan.Periodic_Year =  :in_nYear       







GROUP BY Pool.Loan_Id) TempTab,     







(SELECT Liqui.Loan_Id, MAX(PREPYMT_PENALTY_YLD_MAINT) * MAX(Pool.PERCENT_OWNED) PREPYMT_PENALTY_YLD_MAINT,          







MAX(YLD_MAINT) * MAX(Pool.PERCENT_OWNED) YLD_MAINT,          







MAX(EXIT_FEE) * MAX(Pool.PERCENT_OWNED) EXIT_FEE,          







MAX(DEF_INTEREST_TRUST) * MAX(Pool.PERCENT_OWNED) DEF_INTEREST_TRUST       







FROM T_CMSA_LIQUIDATION Liqui,            







T_CMSA_POOL Pool       







WHERE  Liqui.Loan_Id = Pool.Loan_Id AND       







Pool.INVESTOR_ID = :in_nInvestorId AND       







Liqui.LIQUI_PREPYMT_DT BETWEEN :v_dLowerBndDt AND :out_dDeterminDt      







GROUP BY Liqui.Loan_Id) TempTab1  







WHERE  







Pool.Loan_Id = CompFeed.Loan_Id(+) AND  







Pool.Loan_Id = SplService.Loan_Id(+) AND  







Pool.Loan_Id = TempTab.Loan_Id(+) AND  







Pool.Loan_Id = TempTab1.Loan_Id(+) AND  







Pool.Investor_Id = :in_nInvestorId  







AND (Pool.Loan_id NOT IN (SELECT pl.Loan_Id FROM T_CMSA_PERIODIC_LOAN pl, t_cmsa_pool p                        







WHERE pl.loan_id = p.loan_id and p.investor_id = :in_nInvestorId                        







AND PERIODIC_MONTH = :in_nMonth AND PERIODIC_YEAR = :in_nYear AND BEGIN_SCH_BAL = 0 )                        







AND Pool.Loan_Id NOT IN (SELECT po.Loan_Id FROM V_CMSA_PAYOFF po, t_cmsa_pool p                                







WHERE po.loan_id = p.loan_id and p.investor_id = :in_nInvestorId and ADD_MONTHS(Payoff_Date,1) < :v_dDeterminDt                                







UNION                                







SELECT LOAN_ID FROM T_CMSA_LOAN_SWAP WHERE EFFECTIVE_DT <= :v_dDeterminDt) )  







ORDER BY Pool.Loan_Id ASC )







UNION  







SELECT LOAN_ID ,    







PROSPECTUS_LOAN_ID ,    







BORROWER_NAME ,    







INTERESTRATE ,    







0 V_NSCHINTEREST,    







0 NETRATE_UNSCEDULED_INTREST ,    







0 INTREST_ADJ ,    







0 V_NLESSADJNAGAMORT ,    







0 V_NLESSTOTSERFEE ,    







V_NLESSINTRESAMT ,







--    (V_NSCHINTEREST + NETRATE_UNSCEDULED_INTREST + INTREST_ADJ - V_NLESSADJNAGAMORT - TOTALFEE + V_NLESSINTRESAMT + PREPYMT_PENALTY_YLD_MAINT) NETINTERESTREMITTANCE ,







-- June Start Build







--    (V_NSCHINTEREST + NETRATE_UNSCEDULED_INTREST + INTREST_ADJ - V_NLESSADJNAGAMORT - TOTALFEE + V_NLESSINTRESAMT) NETINTERESTREMITTANCE ,     







V_NLESSINTRESAMT NETINTERESTREMITTANCE ,







-- June Build End    







0 PREPYMT_PENALTY_YLD_MAINT,    







-- cmsa 5.0 wilson paulraj    







YLD_MAINT,    







EXIT_FEE,    







DEF_INTEREST_TRUST







FROM   ( SELECT Pool.Loan_Id LOAN_ID,    







Pool.PROSPECTUS_LOAN_ID PROSPECTUS_LOAN_ID,    







CompFeed.Borrower_Name BORROWER_NAME ,    







NVL(Pkg_Cmsa_Functions.SP_CMSA_LastNoteRate_FNC(:in_nInvestorId, Pool.Loan_Id,:in_nMonth,:in_nYear),0) * 100 "INTERESTRATE",    







NVL(Pkg_Cmsa_Functions.SP_CMSA_SchInterest_FNC(:in_nInvestorId, Pool.Loan_Id,:in_nMonth,:in_nYear),0) V_NSCHINTEREST,    







NVL(TempTab.UNSCH_INTEREST_AMT,0) NETRATE_UNSCEDULED_INTREST,    







NVL(TempTab.INTEREST_ADJ_AMT,0)  INTREST_ADJ,    







NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_NegativeAmort_FNC(:in_nInvestorId,Pool.Loan_Id,:in_nMonth,:in_nYear),0) V_NLESSADJNAGAMORT,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id,1,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(TempTab.v_nPayOffCurtail * PERCENT_OWNED,0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 4,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 5,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.Sp_Cmsa_Workoutfee_Fnc(:in_nInvestorId,Pool.Loan_Id,8,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 2,:in_nMonth,:in_nYear),0),2)    







+ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id, 3,:in_nMonth,:in_nYear),0),2)    







'
    || v_Fee_Qa ||
    ' TOTALFEE,    







Pkg_Cmsa_Reports_Remittance.SP_CMSA_InterestReserve_FNC(:in_nInvestorId,Pool.Loan_Id,:in_nMonth,:in_nYear,CompFeed.INTEREST_BASIS_CD) V_NLESSINTRESAMT ,    







NVL(TempTab1.PREPYMT_PENALTY_YLD_MAINT,0) PREPYMT_PENALTY_YLD_MAINT,        







--CMSA 5.0 By wilson paulraj        







NVL(TempTab1.YLD_MAINT,0) YLD_MAINT,            







NVL(TempTab1.EXIT_FEE,0) EXIT_FEE,                







NVL(TempTab1.DEF_INTEREST_TRUST,0) DEF_INTEREST_TRUST    







FROM T_CMSA_POOL Pool,    







T_CMSA_COMPARISON_FEED CompFeed,    







(SELECT SplServ.Loan_Id Loan_Id FROM T_CMSA_SPECIAL_SERVICING SplServ    







WHERE TRANSFER_DT <= :v_dDeterminDt AND (RETURN_DT IS NULL OR RETURN_DT > :v_dDeterminDt) )SplService,    







(SELECT Pool.Loan_Id,SUM(v_nPayOffCurtail) v_nPayOffCurtail,     







MAX(PrdcLoan.Unsch_Interest_Amt) * MAX(Pool.PERCENT_OWNED) Unsch_Interest_Amt,     







MAX(PrdcLoan.Interest_Adj_Amt) * MAX(Pool.PERCENT_OWNED) Interest_Adj_Amt       







FROM T_CMSA_PERIODIC_LOAN PrdcLoan,        







T_CMSA_POOL Pool,       







(SELECT Liqui.Loan_Id,        







0 v_nPayOffCurtail   







--(NVL(MAX(PrdcLoan.UNSCH_INTEREST_AMT) * MAX(Pool.PERCENT_OWNED),0) + NVL(MAX(PrdcLoan.INTEREST_ADJ_AMT) * MAX(Pool.PERCENT_OWNED),0) ) * NVL(MAX(Pkg_Cmsa_Reports_Remittance.SP_CMSA_TotAdminFeeRate_FNC(in_nInvestorId, Liqui.Loan_Id )),0)/ NVL(MAX(Pkg_Cmsa_Functions.SP_CMSA_LastNoteRate_FNC(in_nInvestorId,Liqui.Loan_Id,in_nMonth,in_nYear)),1) v_nPayOffCurtail        







FROM T_CMSA_LIQUIDATION Liqui,T_CMSA_PERIODIC_LOAN PrdcLoan, T_CMSA_POOL Pool        







WHERE Liqui.Loan_Id = PrdcLoan.Loan_Id AND Liqui.LIQUI_PREPYMT_DT BETWEEN :v_dLowerBndDt AND :out_dDeterminDt        







AND Pool.Investor_Id = :in_nInvestorId AND Pool.Loan_Id = Liqui.Loan_Id        







AND PrdcLoan.Periodic_Month = :in_nMonth AND PrdcLoan.Periodic_Year = :in_nYear GROUP BY Liqui.Loan_Id ) Q1        







WHERE Pool.Loan_Id = PrdcLoan.Loan_Id AND       







Pool.Loan_Id = Q1.Loan_Id(+) AND       







Pool.INVESTOR_ID = :in_nInvestorId AND       







PrdcLoan.Periodic_Month = :in_nMonth AND PrdcLoan.Periodic_Year = :in_nYear        







GROUP BY Pool.Loan_Id) TempTab,        







(SELECT Liqui.Loan_Id, MAX(PREPYMT_PENALTY_YLD_MAINT) * MAX(Pool.PERCENT_OWNED) PREPYMT_PENALTY_YLD_MAINT,        







--cmsa 5.0 ADDED BY WILSON PAULRAJ          







MAX(YLD_MAINT) * MAX(Pool.PERCENT_OWNED) YLD_MAINT,          







MAX(EXIT_FEE) * MAX(Pool.PERCENT_OWNED) EXIT_FEE,          







MAX(DEF_INTEREST_TRUST) * MAX(Pool.PERCENT_OWNED) DEF_INTEREST_TRUST        







FROM T_CMSA_LIQUIDATION Liqui,        







T_CMSA_POOL Pool        







WHERE  Liqui.Loan_Id = Pool.Loan_Id AND        







Pool.INVESTOR_ID = :in_nInvestorId AND        







Liqui.LIQUI_PREPYMT_DT BETWEEN :v_dLowerBndDt AND :out_dDeterminDt        







GROUP BY Liqui.Loan_Id) TempTab1  







WHERE  







Pool.Loan_Id = CompFeed.Loan_Id(+) AND  







Pool.Loan_Id = SplService.Loan_Id(+) AND  







Pool.Loan_Id = TempTab.Loan_Id(+) AND  







Pool.Loan_Id = TempTab1.Loan_Id(+) AND  







Pool.Investor_Id = :in_nInvestorId AND    







(:in_nMonth in (1,2, 3)    







AND Pkg_Cmsa_Reports_Remittance.SP_CMSA_InterestReserve_FNC(:in_nInvestorId,Pool.Loan_Id,:in_nMonth,:in_nYear,CompFeed.INTEREST_BASIS_CD) <> 0)    







AND (Pool.Loan_id IN (SELECT pl.Loan_Id FROM T_CMSA_PERIODIC_LOAN pl, t_cmsa_pool p                        







WHERE pl.loan_id = p.loan_id and p.investor_id = :in_nInvestorId and PERIODIC_MONTH = :in_nMonth                        







AND PERIODIC_YEAR = :in_nYear AND BEGIN_SCH_BAL = 0 ) or                        







Pool.Loan_Id  IN (SELECT po.Loan_Id FROM V_CMSA_PAYOFF po, t_cmsa_pool p                                    







WHERE po.loan_id = p.loan_id and p.investor_id = :in_nInvestorId                                    







and ADD_MONTHS(Payoff_Date,1) <  :v_dDeterminDt ))    







AND POOL.Loan_Id NOT in (SELECT LOAN_ID FROM T_CMSA_LOAN_SWAP WHERE EFFECTIVE_DT <= :v_dDeterminDt)    







ORDER BY Pool.Loan_Id ASC )'
    USING in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    v_dDeterminDt,
    v_dDeterminDt,
    v_dLowerBndDt,
    out_dDeterminDt,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    v_dLowerBndDt,
    out_dDeterminDt,
    in_nInvestorId,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    v_dDeterminDt,
    v_dDeterminDt,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    v_dDeterminDt,
    v_dDeterminDt,
    v_dLowerBndDt,
    out_dDeterminDt,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    v_dLowerBndDt,
    out_dDeterminDt,
    in_nInvestorId,
    in_nMonth,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    v_dDeterminDt,
    v_dDeterminDt ;
    v_cvSerFee:= 'SELECT LOAN_ID ,    







PROSPECTUS_LOAN_ID,    







BORROWER_NAME,    







' ||v_Fee_List ||',    







( MASTER_SERVICE_FEE + PAYOFF_CURTAILMENT_SHORTFALL + SPECIAL_STANDBY_FEE + SPECIAL_SERVICING_FEE + WORKOUT_OTHER_FEE + PRIMARY_SERVICER_FEE + TRUSTEE_FEE '||v_Adm_Fee_List ||
    ') TOTAL_ADMIN_FEE







FROM    







(SELECT DISTINCT Pool.Loan_Id LOAN_ID,    







Pool.PROSPECTUS_LOAN_ID PROSPECTUS_LOAN_ID,    







CompFeed.Borrower_Name BORROWER_NAME,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id,1,:in_nMonth,:in_nYear),0),2) MASTER_SERVICE_FEE,    







--ROUND(NVL(Q1.v_nPayOffCurtail * PERCENT_OWNED ,0),2) V_NPAYOFFCURTAIL,    







0 PAYOFF_CURTAILMENT_SHORTFALL,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id,4, :in_nMonth, :in_nYear),0),2) SPECIAL_STANDBY_FEE,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id,5, :in_nMonth, :in_nYear),0),2) SPECIAL_SERVICING_FEE,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.Sp_Cmsa_Workoutfee_Fnc(:in_nInvestorId,Pool.Loan_Id,8,:in_nMonth, :in_nYear),0),2) WORKOUT_OTHER_FEE,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id,2, :in_nMonth, :in_nYear),0),2) PRIMARY_SERVICER_FEE ,    







ROUND(NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_MasterFee_FNC(:in_nInvestorId,Pool.Loan_Id,3, :in_nMonth, :in_nYear),0),2) TRUSTEE_FEE'
    || v_Fee_Q ||
    '    







FROM    







T_CMSA_POOL Pool,    







T_CMSA_COMPARISON_FEED CompFeed,    







(SELECT SplServ.Loan_Id Loan_Id FROM T_CMSA_SPECIAL_SERVICING SplServ WHERE TRANSFER_DT <= :v_dDeterminDt AND (RETURN_DT IS NULL OR RETURN_DT > :v_dDeterminDt)) SplService,    







(SELECT Liqui.Loan_Id, 0 v_nPayOffCurtail    







-- (NVL(MAX(PrdcLoan.UNSCH_INTEREST_AMT) * MAX(Pool.PERCENT_OWNED),0) + NVL(MAX(PrdcLoan.INTEREST_ADJ_AMT) * MAX(Pool.PERCENT_OWNED),0) ) * NVL(MAX(Pkg_Cmsa_Reports_Remittance.SP_CMSA_TotAdminFeeRate_FNC(in_nInvestorId, Liqui.Loan_Id )),0)/ NVL(MAX(Pkg_Cmsa_Functions.SP_CMSA_LastNoteRate_FNC(in_nInvestorId,Liqui.Loan_Id,in_nMonth,in_nYear)),1) v_nPayOffCurtail   







FROM T_CMSA_LIQUIDATION Liqui,T_CMSA_PERIODIC_LOAN PrdcLoan, T_CMSA_POOL Pool   







WHERE  Liqui.Loan_Id = PrdcLoan.Loan_Id AND Liqui.LIQUI_PREPYMT_DT BETWEEN :v_dLowerBndDt AND :out_dDeterminDt AND    







Pool.Investor_Id = :in_nInvestorId AND Pool.Loan_Id = Liqui.Loan_Id AND PrdcLoan.Periodic_Month = :in_nMonth AND PrdcLoan.Periodic_Year = :in_nYear GROUP BY Liqui.Loan_Id ) Q1    







WHERE Pool.Loan_Id = COMPFEED.Loan_id(+) AND    







Pool.Loan_Id = SplService.Loan_Id(+) AND    







Pool.LOAN_ID = Q1.Loan_Id(+) AND    







POOL.INVESTOR_ID = :in_nInvestorId AND    







Pool.Loan_Id NOT IN (SELECT pl.Loan_Id FROM T_CMSA_PERIODIC_LOAN pl, t_cmsa_pool p                        







WHERE pl.loan_id = p.loan_id and p.investor_id = :in_nInvestorId                        







AND PERIODIC_MONTH = :in_nMonth                        







AND PERIODIC_YEAR = :in_nYear AND BEGIN_SCH_BAL = 0 ) AND    







Pool.Loan_Id NOT IN (SELECT po.Loan_Id FROM V_CMSA_PAYOFF po, t_cmsa_pool p                        







WHERE po.loan_id = p.loan_id and p.investor_id = :in_nInvestorId                        







and ADD_MONTHS(Payoff_Date,1) < :v_dDeterminDt UNION                    







SELECT LOAN_ID FROM T_CMSA_LOAN_SWAP WHERE EFFECTIVE_DT <= :v_dDeterminDt)                    







ORDER BY Pool.Loan_Id ASC)'
    ;
    OPEN out_cvSerFee FOR v_cvSerFee USING in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    v_dDeterminDt,
    v_dDeterminDt,
    v_dLowerBndDt,
    out_dDeterminDt,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    in_nInvestorId,
    in_nMonth,
    in_nYear,
    in_nInvestorId,
    v_dDeterminDt,
    v_dDeterminDt;
    --    v_cvSerFeeName:= 'SELECT ''LOAN_NUMBER,TAB_No, BORROWERS_NAME,' ||v_Fee_List ||',TOTAL_ADMINISTRATIVE_FEES'' FROM DUAL';
    --   OPEN out_cvSerFeeName FOR v_cvSerFeeName;
    OPEN out_cvAdvances FOR SELECT LOAN_ID,
    PROSPECTUS_LOAN_ID,
    BORROWERNAME,
    PAIDTODATE,
    PRIADVBALANCE,
    GROSSINTADVBALANCE ,
    -- For Issue #162 - Advance Tab on Remittance Report should show '0' when Loan is Current.
    --DECODE(PRIADVBALANCE,0,DECODE(GROSSINTADVBALANCE,0,0,NVL(GROSSINTADVBALANCE,0) - NVL(NETINTERESTADVANCE,0) ),NVL(GROSSINTADVBALANCE,0) - NVL(NETINTERESTADVANCE,0) ) ADMINFEEPORTION ,
    NVL(DECODE(Loan_Id,NULL,0, DECODE(Pkg_cmsa_Functions.SP_CMSA_PIAdvance_FNC(in_nInvestorId,Loan_Id,in_nMonth,in_nYear),'Y',0, DECODE(PRIADVBALANCE,0,DECODE(GROSSINTADVBALANCE,0,0,NVL(GROSSINTADVBALANCE,0) - NVL(NETINTERESTADVANCE,0) ),NVL(GROSSINTADVBALANCE,0) - NVL(NETINTERESTADVANCE,0) ))),0) ADMINFEEPORTION,
    -- End Here
    --DECODE(PRIADVBALANCE,0,DECODE(GROSSINTADVBALANCE,0,0,ASERAMT),ASERAMT) ASERAMOUNT ,
    ASERAMT ASERAMOUNT ,
    NETINTERESTADVANCE ,
    TIADVANCE ,
    SERVADVANCE,
    INTONADVACE,
    (PRIADVBALANCE + NETINTERESTADVANCE - DECODE(PRIADVBALANCE,0,DECODE(GROSSINTADVBALANCE,0,0,ASERAMT),ASERAMT) + TIADVANCE + SERVADVANCE + INTONADVACE) TOTALADVBALANCE ,
    ACTUALBALANCE FROM
    (SELECT P.Loan_Id LOAN_ID,
      P.PROSPECTUS_LOAN_ID PROSPECTUS_LOAN_ID,
      CompFeed.Borrower_Name BORROWERNAME,
      --TO_CHAR(DLYFEED.Paid_To_Dt,'YYYYMMDD') PAIDTODATE,
      Pkg_Cmsa_Functions.Sp_Cmsa_Datefrmt_Fnc(DECODE(Pkg_Cmsa_Functions.SP_CMSA_EndSchBal_FNC(in_nInvestorId,DLYFEED.Loan_Id,in_nMonth,in_nYear,NULL),0,DLYFEED.NEXT_PYMT_DT,DLYFEED.PAID_TO_DT)) PAIDTODATE,
      --For Issue#162 --> New Function Created for Principle Advance Balances in Remittance Report.
      --NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_Remittance.SP_CMSA_CumPriAdvBal_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0)*P.PERCENT_OWNED PRIADVBALANCE,
      DECODE(S.Loan_Id,NULL,0, DECODE(Pkg_cmsa_Functions.SP_CMSA_PIAdvance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),'Y',0, NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_CumPriAdvBal_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),0)))*P.PERCENT_OWNED PRIADVBALANCE,
      --NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_Remittance.SP_CMSA_CumIntAdvBal_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0)*P.PERCENT_OWNED GROSSINTADVBALANCE ,
      --NVL(DECODE(S.Loan_Id,NULL,0,PKG_CMSA_Reports_Remittance.SP_CMSA_AdminFee_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0) ADMINFEE ,
      --NVL(DECODE(S.Loan_Id,NULL,0,PKG_CMSA_Reports_Remittance.SP_CMSA_CumASERBal_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0) ASERAMT,
      --NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_Remittance.SP_CMSA_CumASERBalance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0)*P.PERCENT_OWNED ASERAMT,
      --NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_Remittance.SP_CMSA_NetIntAdvance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0)*P.PERCENT_OWNED NETINTERESTADVANCE ,
      NVL(DECODE(S.Loan_Id,NULL,0, DECODE(Pkg_cmsa_Functions.SP_CMSA_PIAdvance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),'Y',0, NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_CumIntAdvBal_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),0))),0)  *P.PERCENT_OWNED GROSSINTADVBALANCE,
      NVL(DECODE(S.Loan_Id,NULL,0, DECODE(Pkg_cmsa_Functions.SP_CMSA_PIAdvance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),'Y',0, NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_CumASERBalance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),0))),0)*P.PERCENT_OWNED ASERAMT,
      NVL(DECODE(S.Loan_Id,NULL,0, DECODE(Pkg_cmsa_Functions.SP_CMSA_PIAdvance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),'Y',0, NVL(Pkg_Cmsa_Reports_Remittance.SP_CMSA_NetIntAdvance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear),0))),0) *P.PERCENT_OWNED NETINTERESTADVANCE,
      -- End #162 Here
      NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_Remittance.SP_CMSA_TIOutStand_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0)               *P.PERCENT_OWNED TIADVANCE ,
      NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_Remittance.SP_CMSA_ServOutStand_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0)             *P.PERCENT_OWNED SERVADVANCE,
      NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_RemitSuppTab.SP_CMSA_IntCumadv_FNC(in_nInvestorId,S.Loan_Id,v_dDeterminDt,in_nMonth,in_nYear)),0)*P.PERCENT_OWNED INTONADVACE,
      --NVL(DECODE(S.Loan_Id,NULL,0,Pkg_Cmsa_Reports_Remittance.SP_CMSA_IntOnAdvance_FNC(in_nInvestorId,S.Loan_Id,in_nMonth,in_nYear)),0)*P.PERCENT_OWNED INTONADVACE,
      NVL(DLYFEED.ACTUAL_BAL * P.PERCENT_OWNED,0) ACTUALBALANCE
    FROM
      (SELECT LOAN_ID
      FROM T_CMSA_POOL
      WHERE Investor_Id = in_nInvestorId
      AND Loan_Id NOT  IN
        (SELECT Loan_Id
        FROM V_CMSA_PAYOFF
        WHERE ADD_MONTHS(Payoff_Date,1) < v_dDeterminDt
        UNION
        SELECT LOAN_ID FROM T_CMSA_LOAN_SWAP WHERE EFFECTIVE_DT <= v_dDeterminDt
        )
      AND Pkg_Cmsa_Reports_Remittance.SP_CMSA_Status_BeginDt_FNC(in_nInvestorId,Loan_Id,in_nMonth,in_nYear) IS NOT NULL
      ) S,
      (SELECT LF.LOAN_ID,
        LF.PAID_TO_DT,
        LF.ACTUAL_BAL,
        DECODE(SIGN(LF.FEED_DT-LF.NEXT_PYMT_DT),1,LF.NEXT_PYMT_DT,LF.PAID_TO_DT) NEXT_PYMT_DT
      FROM T_CMSA_DAILY_LOAN_FEED LF,
        (SELECT F.Loan_Id,
          MAX(F.Feed_Dt) Feed_Dt
        FROM T_CMSA_DAILY_LOAN_FEED F,
          T_CMSA_POOL P
        WHERE P.Investor_Id = in_nInvestorId
        AND P.Loan_Id       = F.Loan_Id
        AND F.Feed_Dt BETWEEN v_dLowerBndDt AND v_dDeterminDt
        GROUP BY F.LOAN_ID
        ) TMP
      WHERE LF.LOAN_ID=TMP.LOAN_ID
      AND LF.FEED_DT  =TMP.FEED_DT
      ) DLYFEED,
      T_CMSA_COMPARISON_FEED CompFeed,
      T_CMSA_POOL P
    WHERE P.Investor_Id = in_nInvestorId
    AND P.Loan_Id NOT  IN
      (SELECT pl.Loan_Id
      FROM T_CMSA_PERIODIC_LOAN pl,
        t_cmsa_pool p
      WHERE pl.loan_id   = p.loan_id
      AND p.investor_id  = in_nInvestorId
      AND PERIODIC_MONTH = in_nMonth
      AND PERIODIC_YEAR  = in_nYear
      AND BEGIN_SCH_BAL  = 0
      )
    AND P.Loan_ID NOT IN
      (SELECT po.Loan_Id
      FROM V_CMSA_PAYOFF po,
        t_cmsa_pool p
      WHERE po.loan_id              = p.loan_id
      AND p.investor_id             = in_nInvestorId
      AND ADD_MONTHS(Payoff_Date,1) < v_dDeterminDt
      UNION
      SELECT LOAN_ID FROM T_CMSA_LOAN_SWAP WHERE EFFECTIVE_DT <= v_dDeterminDt
      )
    AND P.Loan_Id = S.Loan_Id(+)
    AND P.Loan_Id = DLYFEED.Loan_Id(+)
    AND P.Loan_Id = CompFeed.Loan_Id(+)
    ORDER BY P.Loan_Id ASC
    ) ;
    OPEN out_cvMiscellaneous FOR SELECT PL.LOAN_ID,
    PROSPECTUS_LOAN_ID,
    CompFeed.Borrower_Name BORROWERNAME,
    ARA.ASERAMT
  AS
    MOST_RECENT_ASER_AMOUNT,
    TempTab.Unsch_Interest_Amt
  AS
    OTH_SHRTFALL_REFUND,
    Per.REIMB_ADV_SERVICER_CUR_MONTH*PERCENT_OWNED
  AS
    REIMB_ADVANCES,
    Per.CURRENT_PERIOD_ADJ_TRUST*PERCENT_OWNED
  AS
    MINOR_ADJ_TO_TRUST,
    Per.COMMENTS FROM T_CMSA_POOL PL,
    T_CMSA_COMPARISON_FEED CompFeed,
    --        T_CMSA_LIQUIDATION LIQ,
    (
    SELECT Pl.INVESTOR_ID,
      Pl.LOAN_ID,
      (Pkg_Cmsa_Reports_Remittance.SP_CMSA_CumASERBalance_FNC(Pl.INVESTOR_ID,Pl.LOAN_ID,in_nMonth,in_nYear) - Per.CUMULATIVE_ASER_AMT ) ASERAMT
    FROM T_CMSA_PERIODIC_LOAN Per,
      T_CMSA_POOL PL
    WHERE Per.Loan_id                                                                                         = Pl.Loan_id
    AND PERIODIC_MONTH                                                                                        =in_nMonth
    AND PERIODIC_YEAR                                                                                         =in_nYear
    AND Pkg_Cmsa_Reports_Remittance.SP_CMSA_Status_BeginDt_FNC(Pl.INVESTOR_ID,Pl.LOAN_ID,in_nMonth,in_nYear) IS NOT NULL
    AND Pl.Investor_id                                                                                        = in_nInvestorId
    AND Pl.LOAN_ID NOT                                                                                       IN
      (SELECT po.LOAN_ID
      FROM V_CMSA_PAYOFF po,
        t_cmsa_pool p
      WHERE po.loan_id              = p.loan_id
      AND p.investor_id             = in_nInvestorId
      AND ADD_MONTHS(PAYOFF_DATE,1) < v_dDeterminDt
      )
    ) ARA,
    (SELECT P.INVESTOR_ID,
      PL.LOAN_ID,
      PL.ADDL_TRUSTFUND_EXPENSE,
      PL.CURRENT_PERIOD_ADJ_TRUST,
      REIMB_ADV_SERVICER_CUR_MONTH,
      COMMENTS
    FROM T_CMSA_PERIODIC_LOAN PL,
      T_CMSA_POOL P
    WHERE P.Investor_Id = in_nInvestorId
    AND PL.LOAN_ID      =P.LOAN_ID
    AND PERIODIC_MONTH  =in_nMonth
    AND PERIODIC_YEAR   =in_nYear
    ) Per,
    (SELECT Pool.Loan_Id,
      MAX(PrdcLoan.Unsch_Interest_Amt) * MAX(Pool.PERCENT_OWNED) Unsch_Interest_Amt
    FROM T_CMSA_PERIODIC_LOAN PrdcLoan,
      T_CMSA_POOL Pool,
      (SELECT Liqui.Loan_Id,
        (NVL(MAX(PrdcLoan.UNSCH_INTEREST_AMT) * MAX(Pool.PERCENT_OWNED),0) + NVL(MAX(PrdcLoan.INTEREST_ADJ_AMT) * MAX(Pool.PERCENT_OWNED),0) ) * NVL(MAX(Pkg_Cmsa_Reports_Remittance.SP_CMSA_TotAdminFeeRate_FNC(in_nInvestorId, Liqui.Loan_Id )),0)/ NVL(MAX(Pkg_Cmsa_Functions.SP_CMSA_LastNoteRate_FNC(in_nInvestorId,Liqui.Loan_Id,in_nMonth,in_nYear)),1) v_nPayOffCurtail
      FROM T_CMSA_LIQUIDATION Liqui,
        T_CMSA_PERIODIC_LOAN PrdcLoan,
        T_CMSA_POOL Pool
      WHERE Liqui.Loan_Id = PrdcLoan.Loan_Id
      AND Liqui.LIQUI_PREPYMT_DT BETWEEN v_dLowerBndDt AND out_dDeterminDt
      AND Pool.Investor_Id        = in_nInvestorId
      AND Pool.Loan_Id            = Liqui.Loan_Id
      AND PrdcLoan.Periodic_Month = in_nMonth
      AND PrdcLoan.Periodic_Year  = in_nYear
      GROUP BY Liqui.Loan_Id
      ) Q1
    WHERE Pool.Loan_Id          = PrdcLoan.Loan_Id
    AND Pool.Loan_Id            = Q1.Loan_Id(+)
    AND Pool.INVESTOR_ID        = in_nInvestorId
    AND PrdcLoan.Periodic_Month = in_nMonth
    AND PrdcLoan.Periodic_Year  = in_nYear
    GROUP BY Pool.Loan_Id
    ) TempTab WHERE PL.Investor_Id = in_nInvestorId AND PL.Loan_Id = CompFeed.Loan_Id(+) AND PL.INVESTOR_ID = ARA.INVESTOR_ID (+) AND PL.Loan_id = ARA.Loan_id (+) AND PL.INVESTOR_ID = Per.INVESTOR_ID (+) AND PL.Loan_id = Per.Loan_id (+) AND PL.Loan_id = TempTab.Loan_id (+)
    --    AND PL.Loan_Id NOT IN (SELECT Loan_Id FROM V_CMSA_PAYOFF WHERE ADD_MONTHS(Payoff_Date,1) < v_dDeterminDt UNION
    --                        SELECT LOAN_ID FROM T_CMSA_LOAN_SWAP WHERE EFFECTIVE_DT <= v_dDeterminDt)
    AND ( NVL(ARA.ASERAMT,0) <> 0 OR NVL(Per.REIMB_ADV_SERVICER_CUR_MONTH,0) <> 0 OR NVL(Per.CURRENT_PERIOD_ADJ_TRUST,0) <> 0 OR NVL(TempTab.Unsch_Interest_Amt,0) <>0)
    --    AND PL.LOAN_ID = LIQ.LOAN_ID(+)
    ORDER BY PL.Loan_Id ASC ;
  EXCEPTION
  WHEN OTHERS THEN
    out_nStatusCode := 1;                                                                                          -- Send fail status back.
    Pkg_Cmsa_Errorhandling.SP_CMSA_LogErrMsg('Error found during the Remittance Report creation --> ' || SQLERRM); -- log error message from main block into temp table
    Pkg_Cmsa_Errorhandling.SP_CMSA_GetErrMsg(out_cvAdvances);                                                      -- retrieve all errors in cusror variable
  END;
END;