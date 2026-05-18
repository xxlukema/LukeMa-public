SELECT
    c.Calendar,
    stg.*
FROM
    ETL_FionaToC3_Staging_OSARReview stg,
    Calendar c
WHERE
    c.CalendarID = stg.CalendarID
AND rowid = 310876;
--
SELECT
    rowid,
    AnalystApproved
FROM
    ETL_FionaToC3_Staging_OSARReview
WHERE
    AnalystApproved LIKE 'N%';