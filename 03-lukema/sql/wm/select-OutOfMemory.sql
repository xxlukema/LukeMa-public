SELECT
    *
FROM
    TP_SYSLOGENTRY
WHERE
    --TEXT LIKE '%OutOfMemory%';
    TEXT LIKE '%ByteArrayOutputStream.toByteArray%';