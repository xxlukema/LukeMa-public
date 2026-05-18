SET serveroutput ON format wrapped;
DECLARE

    in_nInvestorId NUMBER := 771;
in_nRepmonth NUMBER := 12;
in_nRepyear NUMBER := 2012;


out_dAsOfDt VARCHAR2(20);
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
DBMS_OUTPUT.put_line('msg: ' || 'a');
DBMS_OUTPUT.put_line('v_AsOfDate: ' || v_AsOfDate);
DBMS_OUTPUT.put_line('V_DLOWERBNDDT: ' || V_DLOWERBNDDT);
DBMS_OUTPUT.put_line('IN_NREPYEAR: ' || IN_NREPYEAR);
DBMS_OUTPUT.put_line('IN_NREPMONTH: ' || IN_NREPMONTH);
--
--


END;
 