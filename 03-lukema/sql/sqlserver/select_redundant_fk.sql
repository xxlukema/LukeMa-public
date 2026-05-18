SELECT
    c.ClientID         AS c_ClientID,
    i.ClientID         AS i_ClientID,
    l.ClientID         AS l_ClientID,
    i.FrequencyCD      AS i_FrequencyCD,
    l.FrequencyCD      AS l_FrequencyCD,
    p.FrequencyCD      AS p_FrequencyCD,
    i.AnalysisMethodCD AS i_AnalysisMethodCD,
    l.AnalysisMethodCD AS l_AnalysisMethodCD,
    p.AnalysisMethodCD AS p_AnalysisMethodCD
FROM
    Client c,
    Loan l,
    Investor i,
    Property p
WHERE
    c.ClientID = i.ClientID
AND l.InvestorID = i.InvestorID
AND p.LoanID = l.LoanID
    --AND l.LoanID= 3316