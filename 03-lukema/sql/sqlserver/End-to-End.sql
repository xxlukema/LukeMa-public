--1
--CMGClient
SELECT
    *
FROM
    CMGClient
WHERE
    CMGName LIKE 'test%';
--2
--Client
SELECT
    *
FROM
    Client
WHERE
    CMGClientId = 2;
--3
--Calendar
SELECT
    *
FROM
    Calendar
WHERE
    ClientID = 18;
--4
--Investor
SELECT
    *
FROM
    Investor
WHERE
    ClientID = 18;
--5
--Loan
SELECT
    *
FROM
    Loan
WHERE
    ClientID = 18;
--6
--Property
SELECT
    *
FROM
    Property
WHERE
    LoanID IN
    (
        SELECT
            ClientID
        FROM
            Loan
        WHERE
            ClientID = 18);
--7
--Multi Property
SELECT
    LoanID,
    COUNT(PropertyID)
FROM
    Property
GROUP BY
    LoanID
HAVING
    COUNT(PropertyID) > 1
ORDER BY
    COUNT(PropertyID) DESC;
--8
--SUM Property
SELECT
    *
FROM
    Property
WHERE
    LoanID = 153;