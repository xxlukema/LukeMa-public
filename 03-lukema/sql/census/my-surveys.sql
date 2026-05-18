--
-- 1. User_Account
SELECT
    USER_ACCOUNT_ID
FROM
    USER_ACCOUNT
WHERE
    EMAIL LIKE 'x.luke.ma%';
--
-- 2. Survey_Instance
SELECT
    SURVEY_INSTANCE_ID, USER_ACCOUNT_ID
FROM
    SURVEY_USER
WHERE
    USER_ACCOUNT_ID IN
    (
        SELECT
            USER_ACCOUNT_ID
        FROM
            USER_ACCOUNT
        WHERE
            EMAIL LIKE 'x.luke.ma%');
--
-- 3. survey_id(s)
SELECT
    *
FROM
    SURVEY_INSTANCE
WHERE
    SURVEY_INSTANCE_ID IN
    (
        SELECT
            SURVEY_INSTANCE_ID
        FROM
            SURVEY_USER
        WHERE
            USER_ACCOUNT_ID IN
            (
                SELECT
                    USER_ACCOUNT_ID
                FROM
                    USER_ACCOUNT
                WHERE
                    EMAIL LIKE 'x.luke.ma%') );
--
-- 4.
SELECT
    SURVEY_ID
FROM
    SURVEY_INSTANCE
WHERE
    SURVEY_INSTANCE_ID IN
    (
        SELECT
            SURVEY_INSTANCE_ID
        FROM
            SURVEY_USER
        WHERE
            USER_ACCOUNT_ID IN
            (
                SELECT
                    USER_ACCOUNT_ID
                FROM
                    USER_ACCOUNT
                WHERE
                    EMAIL LIKE 'x.luke.ma%') );
--
-- 5. Survey_Type
SELECT
    SURVEY_TYPE_ID
FROM
    SURVEY
WHERE
    SURVEY_ID IN
    (
        SELECT
            SURVEY_ID
        FROM
            SURVEY_INSTANCE
        WHERE
            SURVEY_INSTANCE_ID IN
            (
                SELECT
                    SURVEY_INSTANCE_ID
                FROM
                    SURVEY_USER
                WHERE
                    USER_ACCOUNT_ID IN
                    (
                        SELECT
                            USER_ACCOUNT_ID
                        FROM
                            USER_ACCOUNT
                        WHERE
                            EMAIL LIKE 'x.luke.ma%') ) );
--
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
            SURVEY_ID IN
            (
                SELECT
                    SURVEY_ID
                FROM
                    SURVEY_INSTANCE
                WHERE
                    SURVEY_INSTANCE_ID IN
                    (
                        SELECT
                            SURVEY_INSTANCE_ID
                        FROM
                            SURVEY_USER
                        WHERE
                            USER_ACCOUNT_ID IN
                            (
                                SELECT
                                    USER_ACCOUNT_ID
                                FROM
                                    USER_ACCOUNT
                                WHERE
                                    EMAIL LIKE 'x.luke.ma%') ) ) );
    --
    -- select CENTURION_URL from SURVEY_TYPE where