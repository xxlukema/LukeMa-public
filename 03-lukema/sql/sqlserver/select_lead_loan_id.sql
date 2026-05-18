SELECT
    L.LoanID,
    LL.LoanID AS 'LeadLoanID',
    L.LeadLoanFlag,
    LL.LeadLoanFlag AS 'LLFlag'
FROM
    Loan L
LEFT JOIN
    Loan LL
ON
    L.LeadLoanID = LL.LoanID
--WHERE L.LeadLoanID IS NOT NULL
ORDER BY
    2