SELECT
    COUNT(1) as Rows
FROM
    ETL_FionaToC3_InvalidRow_PropertyProfile
WHERE
    RowID = ?;
