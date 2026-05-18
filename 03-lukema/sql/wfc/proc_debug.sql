SET serveroutput ON format wrapped;
DECLARE
  v_nIntFlag CHAR(1);
  v_nPIConst NUMBER ;
  v_InterestEndDt DATE ;
  v_ModiDt DATE ;
  v_DeterminationDt DATE ;
  v_IntRateType T_CMSA_LOAN.Interest_Rate_Type%TYPE ;
  v_dLowerBndDt DATE;
  v_nActualBal NUMBER := 1;
  v_dPrevMonthDt DATE ;
  v_dPaidoffDt DATE;
  v_cPIConstflag  CHAR(1);
  v_nPercentOwned NUMBER;
  --
  in_nInvestorId NUMBER;
  in_nLoanID     NUMBER;
  in_nRepMonth   NUMBER;
  in_nRepYear    NUMBER;
BEGIN
  in_nInvestorId := 560;
  in_nLoanID     := 600873524;
  in_nRepMonth   := 8;
  in_nRepYear    := 2012;
  SELECT INTEREST_FLAG,
    Interest_End_Dt,
    Interest_Rate_Type
  INTO v_nIntFlag,
    v_InterestEndDt,
    v_IntRateType
  FROM T_CMSA_LOAN
  WHERE LOAN_ID      = in_nLoanId ;
  
  DBMS_OUTPUT.put_line('11111 v_nIntFlag = ' || v_nIntFlag);
  
  v_DeterminationDt := Pkg_Cmsa_Functions.SP_CMSA_RepDeterDt_FNC(in_nInvestorId,in_nRepMonth,in_nRepYear) ;
  v_dPrevMonthDt    := ADD_MONTHS(v_DeterminationDt,                                                                                                                    -1) ;
  v_dLowerBndDt     := NVL(Pkg_Cmsa_Functions.SP_CMSA_RepDeterDt_FNC(in_nInvestorId,TO_NUMBER(TO_CHAR(v_dPrevMonthDt,'MM')),TO_NUMBER(TO_CHAR(v_dPrevMonthDt,'YYYY')) ) + 1, v_dPrevMonthDt +1) ;
  
  DBMS_OUTPUT.put_line('11111a v_DeterminationDt = ' || v_DeterminationDt);
  DBMS_OUTPUT.put_line('11111b v_dPrevMonthDt = ' || v_dPrevMonthDt);
  DBMS_OUTPUT.put_line('11111c v_dLowerBndDt = ' || v_dLowerBndDt);
  
  BEGIN
  
    DBMS_OUTPUT.put_line('222222- v_nActualBal = ' || v_nActualBal); 
    
    SELECT ACTUAL_BAL
    INTO v_nActualBal
    FROM T_CMSA_DAILY_LOAN_FEED
    WHERE Loan_Id = in_nLoanId
    AND Feed_Dt   =
      (SELECT MAX(Feed_Dt)
      FROM T_CMSA_DAILY_LOAN_FEED
      WHERE Loan_Id = in_nLoanId
      AND Feed_Dt BETWEEN v_dLowerBndDt AND v_DeterminationDt
      ) ;
      
    DBMS_OUTPUT.put_line('222222a in_nLoanId = ' || in_nLoanId);
    DBMS_OUTPUT.put_line('222222a v_nActualBal = ' || v_nActualBal); 
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    v_nActualBal := NULL ;
  END ;
  
  DBMS_OUTPUT.put_line('222222b v_nActualBal = ' || v_nActualBal);
  
  BEGIN
    SELECT PAYOFF_DATE
    INTO v_dPaidoffDt
    FROM V_CMSA_PAYOFF
    WHERE Loan_Id = in_nLoanId;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    v_dPaidoffDt := NULL ;
  END ;
  
  DBMS_OUTPUT.put_line('333333 v_dPaidoffDt = ' || v_dPaidoffDt);
  
  IF (v_nActualBal  =0 OR v_nActualBal IS NULL) AND ( v_dPaidoffDt>=v_dLowerBndDt AND v_dPaidoffDt<=v_DeterminationDt) THEN
    v_cPIConstflag := 'Y';
  ELSIF v_nActualBal=0 OR v_nActualBal IS NULL THEN
    v_cPIConstflag := 'N';
    v_nPIConst     := NULL ;
  ELSE
    v_cPIConstflag := 'Y';
  END IF;
  
  DBMS_OUTPUT.put_line('4444-- v_cPIConstflag = ' || v_cPIConstflag);
  
  IF ( v_cPIConstflag     = 'Y' ) THEN
    IF (UPPER(v_nIntFlag) = 'N' OR v_nIntFlag IS NULL) AND v_IntRateType = Pkg_Cmsa_Commdefi.v_ARM_IntType THEN
      BEGIN
        SELECT Schedule_PI
        INTO v_nPIConst     -- 1111
        FROM T_CMSA_ARM_PI
        WHERE LOAN_ID      = in_nLoanID
        AND Payment_Adj_Dt =
          (SELECT MAX(Payment_Adj_Dt)
          FROM T_CMSA_ARM_PI
          WHERE Loan_Id       = in_nLoanID
          AND Payment_Adj_Dt <= v_DeterminationDt
          ) ;
        SELECT PERCENT_OWNED
        INTO v_nPercentOwned
        FROM T_CMSA_POOL
        WHERE INVESTOR_ID=in_nInvestorId
        AND LOAN_ID      =in_nLoanId;
        DBMS_OUTPUT.put_line('111 v_nPIConst * v_nPercentOwned = ' || (v_nPIConst * v_nPercentOwned));
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_nPIConst := NULL ;
      END ;
      
      DBMS_OUTPUT.put_line('444444 v_nPIConst = ' || v_nPIConst);
      
    END IF ;
    
    IF UPPER(v_nIntFlag) = 'Y' AND (v_InterestEndDt >= v_DeterminationDt OR v_InterestEndDt IS NULL) THEN
      v_nPIConst        := Pkg_Cmsa_Functions.SP_CMSA_SchInterest_FNC (in_nInvestorId, in_nLoanId,in_nRepMonth,in_nRepYear) ;   -- 2222
    ELSE
      SELECT MAX(Modification_Dt)
      INTO v_ModiDt
      FROM
        (SELECT Modification_Dt
        FROM T_CMSA_LOAN_MODIFICATION
        WHERE Loan_Id        = in_nLoanID
        AND Modification_Dt <= v_DeterminationDt
        AND New_P_I         IS NOT NULL
        );
      IF v_ModiDt IS NOT NULL THEN
        SELECT LoanMod.New_P_I
        INTO v_nPIConst       -- 3333
        FROM T_CMSA_LOAN_MODIFICATION LoanMod
        WHERE LoanMod.Loan_id       = in_nLoanID
        AND LoanMod.Modification_Dt = v_ModiDt ;
      ELSE
        SELECT SUM(Pool.PI_Contribution)
        INTO v_nPIConst      -- 4444
        FROM T_CMSA_POOL Pool
        WHERE Pool.LOAN_ID   = in_nLoanID
        AND Pool.investor_id = in_nInvestorId;
      END IF;
    END IF ;
  END IF;
  
  SELECT PERCENT_OWNED
  INTO v_nPercentOwned
  FROM T_CMSA_POOL
  WHERE INVESTOR_ID=in_nInvestorId
  AND LOAN_ID      =in_nLoanId;
  
  DBMS_OUTPUT.put_line('555555 v_nPercentOwned = ' || v_nPercentOwned);
  
  DBMS_OUTPUT.put_line('222 v_nPIConst * v_nPercentOwned = ' || (v_nPIConst * v_nPercentOwned));
  
  DBMS_OUTPUT.put_line('----------------------');
  DBMS_OUTPUT.put_line('');

EXCEPTION
WHEN OTHERS THEN
  DBMS_OUTPUT.put_line('Null');
  

END;
