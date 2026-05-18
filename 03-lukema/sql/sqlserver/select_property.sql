SELECT
    p.*
FROM
    Property p,
    Loan l,
    Investor i
WHERE
    p.LoanID = l.LoanID
AND l.InvestorID = i.InvestorID
AND i.InvestorNumber = 10151