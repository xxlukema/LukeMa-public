
SELECT
    a.run_number ID,
    TO_CHAR (a.start_datetime, 'DD-MON-YYYY HH24:MI') descr
FROM
    cs_cms_run_control a
WHERE
    a.run_category = 'INTRA_CEL'
AND a.start_datetime =
    (
        SELECT
            MAX (b.start_datetime)
        FROM
            cs_cms_run_control b
        WHERE
            b.run_category = 'INTRA_CEL'
        AND b.run_status IN ('RUNNING', 'COMPLETED')
    ) ;

----------------------------------------------------------------

SELECT
    FILE_ID SOURCE_FEED_NAME,
    TO_CHAR(MAX(INPUT_DATETIME), 'DD-MON-YYYY') FEED_COB,
    TO_CHAR(MAX(LOAD_DATETIME), 'DD-MON-YYYY HH24:MI') LOAD_TIME,
    '' IS_AFTER
FROM
    CS_LOAD_STATUS
WHERE
    FILE_ID IN
    (
        SELECT
            FILE_ID
        FROM
            CS_FILE_ID
        WHERE
            FILE_GROUP IN
            (
                SELECT
                    text
                FROM
                    cs_col_texts
                WHERE
                    code = 'DER_FILE_GROUP'
            )
    )
AND FILE_ID != 'SCIR5671'
AND LOAD_DATETIME IS NOT NULL
AND INPUT_DATETIME IS NOT NULL
GROUP BY
    FILE_ID
ORDER BY
    FILE_ID ASC ;

----------------------------------------------------------------

SELECT DISTINCT
    ( run_number ) id,
    '' descr
FROM
    CS_run_status
WHERE
    success_flg = 'Y'
AND run_category = 'INTRA_CEL'
AND cob_date >= sysdate - 60
AND data_purged IS NULL
AND RUN_TYPE != 'SOD_CEL'
ORDER BY
    run_number DESC ;

----------------------------------------------------------------

SELECT DISTINCT
    ( cob_date ) id,
    TO_CHAR(cob_date, 'dd-mon-yyyy') descr
FROM
    cs_run_status
WHERE
    success_flg = 'Y'
AND run_category = 'INTRA_CEL'
AND cob_date >= sysdate - 60
AND data_purged IS NULL
AND RUN_TYPE != 'SOD_CEL'
ORDER BY
    cob_date DESC ;

----------------------------------------------------------------

SELECT DISTINCT
    ( r.run_number ) id,
    '' descr
FROM
    cs_run_status r,
    cs_stream_queue s
WHERE
    r.success_flg = 'Y'
AND r.run_category = 'INTRA_CEL'
AND R.RUN_TYPE != 'SOD_CEL'
AND r.cob_date >= sysdate - 60
AND r.data_purged IS NULL
AND r.run_number = s.run_number
AND r.run_category = s.run_category
AND s.status = 'SUCCESS'
ORDER BY
    r.run_number DESC ;

----------------------------------------------------------------

SELECT
    TEXT INST_CODE,
    '' LEGAL_NAME
FROM
    CS_COL_TEXTS
WHERE
    CODE = 'REGION'
ORDER BY
    SEQUENCE ;

----------------------------------------------------------------

SELECT
    A.RUN_CATEGORY RUN_CATEGORY,
    A.RUN_STATUS RUN_STATUS,
    A.RUN_NUMBER RUN_NUMBER,
    TO_CHAR(B.COB_DATE, 'DD-MON-YYYY') COB_DATE,
    TO_CHAR(A.START_DATETIME, 'DD-MON-YYYY HH24:MI') START_TIME,
    TO_CHAR(A.END_DATETIME, 'DD-MON-YYYY HH24:MI') END_TIME,
    REGION REGION
FROM
    cs_CMS_RUN_CONTROL A,
    CS_RUN_STATUS B
WHERE
    A.RUN_NUMBER = B.RUN_NUMBER(+)
AND B.RUN_TYPE (+) != 'SOD_CEL'
AND B.run_category(+) = 'INTRA_CEL' ;

----------------------------------------------------------------

/*
INTRA_CEL_REP AMERICA
INTRA_CEL_REP EUROPE
INTRA_CEL_REP ASIA
INTRA_CEL_REP OTHER
INTRA_CEL_REP INDIA
*/

