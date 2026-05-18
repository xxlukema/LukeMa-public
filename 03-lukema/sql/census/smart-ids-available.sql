SELECT
    si.SURVEY_INSTANCE_ID,
    si.SMART_ID,
    s.DISPLAY_NAME,
    s.MAINT_DTM,
    s.SURVEY_ID,
    so.SURVEY_OPTIONS_ID
FROM
    SURVEY_INSTANCE si,
    SURVEY_SCHEDULE ss,
    SURVEY          s,
    SURVEY_TYPE     st,
    SURVEY_OPTIONS  so
WHERE
    si.SURVEY_ID = s.SURVEY_ID
AND s.SURVEY_ID = ss.SURVEY_ID
AND s.SURVEY_ID = so.SURVEY_ID
AND st.SURVEY_TYPE_ID = s.SURVEY_TYPE_ID
AND st.ODSY_SURVEY_FLG IS NULL
AND si.STATUS_INDICATOR IN (1,
                            2)
AND si.SURVEY_INSTANCE_ID NOT IN
    (   SELECT
            si.SURVEY_INSTANCE_ID
        FROM
            SURVEY_USER     su,
            SURVEY_INSTANCE si
        WHERE
            su.SURVEY_INSTANCE_ID = si.SURVEY_INSTANCE_ID )
ORDER BY
    s.MAINT_DTM, 
    si.SMART_ID