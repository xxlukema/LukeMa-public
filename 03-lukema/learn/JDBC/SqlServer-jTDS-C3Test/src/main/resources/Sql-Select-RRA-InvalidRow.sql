SELECT
    RowID
FROM
    ETL_FionaToC3_InvalidRow_RRA
WHERE
    PropertyID = ?
AND CalendarID = ?;