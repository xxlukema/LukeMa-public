--
--1. Loan
SELECT
    LoanID,
    
    COUNT(PropertyID) AS PropertyID_Count
FROM
    Property
GROUP BY
    LoanID
HAVING
    COUNT(PropertyID) > 2
ORDER BY
    COUNT(PropertyID) DESC;
--
--2. Investor
SELECT
    InvestorID,
    ClientID,
    InvestorNumber,
    InvestorDescription
FROM
    Investor
WHERE
    InvestorID =
    (
        SELECT
            InvestorID
        FROM
            Loan
        WHERE
            LoanID = 12802);
--
--3. LoanNumber
SELECT
    LoanID,
    ClientID,
    LoanNumber,
    InvestorID,
    AnalysisMethodCD
FROM
    Loan
WHERE
    LoanID = 12802;
--
--4. Property
SELECT
    PropertyID,
    PropertyName,
    PropertyNumber,
    LoanID,
    propAddr1
FROM
    Property
WHERE
    LoanID = 12802;
