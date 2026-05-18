UPDATE
    cs_load_status
SET
    LOAD_DATETIME = NULL
WHERE
    FILE_ID = 'GMIMARGIN'
AND input_datetime = '26-May-2011';