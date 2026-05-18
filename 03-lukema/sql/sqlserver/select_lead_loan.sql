--1. Loan
SELECT
    ll.* ,
    fl.*
FROM
    Loan ll
LEFT JOIN
    loan fl
ON
    ll.LoanID = fl.LeadLoanID
WHERE
    ll.LeadLoanFlag = 1 ;
--2. Property
SELECT
    *
FROM
    Property
WHERE
    LoanID IN
    (
        SELECT
            fl.LoanID
        FROM
            Loan ll
        LEFT JOIN
            loan fl
        ON
            ll.LoanID = fl.LeadLoanID
        WHERE
            ll.LeadLoanFlag = 1 );
--3. Count
SELECT
    LoanID,
    COUNT(*) property_count
FROM
    Property
WHERE
    LoanID IN
    (
        SELECT
            ll.LoanID
        FROM
            Loan ll
        LEFT JOIN
            loan fl
        ON
            ll.LoanID = fl.LeadLoanID
        WHERE
            ll.LeadLoanFlag = 1 )
GROUP BY
    LoanID
ORDER BY
    COUNT(*) DESC;
--4. Loan: Lead   38735
--4. Loan: Follow 63334 76496
SELECT
    ll.* ,
    fl.*
FROM
    Loan ll
RIGHT OUTER JOIN
    loan fl
ON
    ll.LoanID = fl.LeadLoanID
WHERE
    fl.LoanID IN (38735,
                  63334,
                  76496) ;
--5. Property Lead loan   38735
--5. Property Follow loan 63334 76496
SELECT
    *
FROM
    Property
WHERE
    LoanID = 38735
OR  LoanId IN
    (
        SELECT
            fl.LoanID
        FROM
            Loan ll
        LEFT JOIN
            loan fl
        ON
            ll.LoanID = fl.LeadLoanID
        WHERE
            ll.LeadLoanFlag = 1
        AND ll.LoanID = 38735 )
ORDER BY
    LoanID,
    PropertyNumber,
    PropertyID;
--6. Investor, Loan, Property
--6. Loan: Lead   38735
--6. Loan: Follow 63334 76496
SELECT
    i.*,
    l.*,
    p.*
FROM
    Investor i,
    Loan l,
    Property p
WHERE
    i.InvestorID = l.InvestorID
AND p.LoanID = l.LoanID
AND l.LoanId IN (38735,
                 63334,
                 76496);
--
--