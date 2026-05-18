SELECT
    p.PropertyID,
    p.SpreadInstructions
FROM
    Property p,
    Loan l,
    Investor i
WHERE
    i.InvestorID = l.InvestorID
AND l.LoanID = p.LoanID
AND i.InvestorNumber = ?
AND l.LoanNumber = ?
AND p.PropertyNumber=?
AND p.ProspectusIDNumber=?;