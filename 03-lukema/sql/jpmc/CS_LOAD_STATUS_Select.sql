SELECT
    *
FROM
    cs_load_status
WHERE
    FILE_ID = 'GMIMARGIN'
AND LOAD_DATETIME IS NOT NULL
--AND input_datetime = '26-May-2011'
ORDER BY
    input_datetime DESC