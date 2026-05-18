SELECT
    *
FROM
    ETL_FionaToC3_Staging_Collections
WHERE
    PropertyID IN
    (
        SELECT
            PropertyID
        FROM
            Property
        WHERE
            LoanID IN
            (
                SELECT
                    p.LoanID
                FROM
                    Property p,
                    ETL_FionaToC3_Staging_Collections s
                WHERE
                    p.PropertyID = s.PropertyID
                GROUP BY
                    p.LoanID
                HAVING
                    COUNT(p.PropertyID) = 1))
ORDER BY
    PropertyID