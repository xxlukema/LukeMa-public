SELECT
    *
FROM
    CARRIER
WHERE
    CARRIERID IN
    (
        SELECT
            CARRIERID
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
                    SAC = '391657') );