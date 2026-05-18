SELECT
    *
FROM
    TP_SYSLOGENTRY
WHERE
    --TEXT LIKE '%OutOfMemory%';
    --TEXT LIKE '%ByteArrayOutputStream.toByteArray%';
    CREATED > to_date('2013-04-05', 'yyyy-mm-dd')
AND KIND != 'H'
ORDER BY
    CREATED; 