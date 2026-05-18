--
-- 1. Survey -- 204, 629
SELECT
    *
FROM
    SURVEY
WHERE
    SURVEY_ID = '629';
--
-- 2. Survey_Type
SELECT
    *
FROM
    SURVEY_TYPE
WHERE
    SURVEY_TYPE_ID IN
    (
        SELECT
            SURVEY_TYPE_ID
        FROM
            SURVEY
        WHERE
            SURVEY_ID = '629'
);
-- 3. Survey_Options
SELECT
    *
FROM
    SURVEY_OPTIONS
WHERE
    SURVEY_ID = '629';
--
-- 4. Survey_Schedule
SELECT
    *
FROM
    SURVEY_SCHEDULE
WHERE
    SURVEY_ID = '629';
--
-- 5. Survey_Config
SELECT
    *
FROM
    SURVEY_CONFIG
WHERE
    SURVEY_ID = '629';
--
-- 6. Survey_Instance
SELECT
    *
FROM
    SURVEY_INSTANCE
WHERE
    SURVEY_ID = '629';


