INSERT
INTO
    CS_STREAM_QUEUE
    (
        RUN_CATEGORY,
        SEQUENCE,
        STATUS,
        ARG1,
        ARG2,
        COB_DATE,
        AS_OF_COB_DATE
    )
    VALUES
    (
        'CFTC_STMT',
        (
            SELECT
                NVL(MAX(SEQUENCE)+1, 0)
            FROM
                CS_STREAM_QUEUE
            WHERE
                RUN_CATEGORY='CFTC_STMT'
        )
        ,
        'WAITING',
        'CEL_CALC',
        'GMIMARGIN',
        to_date('110606', 'YYMMDD'),
        to_date('110606', 'YYMMDD')
    );

SELECT
    *
FROM
    CS_STREAM_QUEUE
WHERE
    RUN_CATEGORY = 'CFTC_STMT'
ORDER BY
    COB_DATE DESC;

