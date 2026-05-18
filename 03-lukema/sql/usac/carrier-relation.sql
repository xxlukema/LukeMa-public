SELECT
    *
FROM
    CARRIERRELATIONSHIP
WHERE
    STUDYAREAID IN
    (
        SELECT
            STUDYAREAID
        FROM
            STUDYAREA
        WHERE
            SAC = '391657') ;