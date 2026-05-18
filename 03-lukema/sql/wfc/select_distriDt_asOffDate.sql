SELECT
    DC.Reporting_Year,
    DC.Reporting_Month,
    MAX(Report_Distri_Dt)                 AS v_DistriDt,
    MAX(Report_Deter_Dt)                  AS v_dAsofDate,
    MAX(ADD_MONTHS(Report_Deter_Dt,-1))+1 AS v_dLowerBndDt
FROM
    T_CMSA_DISTRIBUTION_CALENDAR DC,
    T_CMSA_POOL P
WHERE
    P.Investor_Id = DC.Investor_ID
AND DC.Reporting_Month >= '01'
AND DC.Reporting_Year = '2012'
AND P.Loan_Id = 110202647
GROUP BY
    DC.Reporting_Year,
    DC.Reporting_Month
ORDER BY
    1,2;