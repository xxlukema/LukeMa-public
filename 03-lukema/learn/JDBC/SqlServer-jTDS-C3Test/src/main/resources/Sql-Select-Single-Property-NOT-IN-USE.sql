SELECT
    DISTINCT PropertyID
FROM
    Property
WHERE
    LoanID IN
    (
        SELECT
            LoanID
        FROM
            Property
        GROUP BY
            LoanID
        HAVING
            COUNT(PropertyID) = 1);