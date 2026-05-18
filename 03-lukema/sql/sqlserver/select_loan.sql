SELECT
    l.InvestorID,
    COUNT(l.LoanID)
FROM
    Loan l,
    Investor i
WHERE
    l.InvestorID = i.InvestorID
GROUP BY
    l.InvestorID