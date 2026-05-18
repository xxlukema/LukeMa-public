SELECT
    s.SAC,
    s.NAME AS sacName,
    cr.SPIN,
    cr.NAME                                     AS spinName,
    TO_CHAR(li.appliesToMonth, 'FMMonth, YYYY') AS appliesToMonth,
    TO_CHAR(li.enteredMonth, 'FMMonth, YYYY')   AS enteredMonth,
    CASE
        WHEN (ll.COMMENTS IS NOT NULL
            OR  lu.comments IS NOT NULL
            OR  tl.COMMENTS IS NOT NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) > 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) = 1
        THEN 'AADJ*'
        WHEN (ll.COMMENTS IS NOT NULL
            OR  lu.comments IS NOT NULL
            OR  tl.COMMENTS IS NOT NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) = 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) = 1
        THEN 'A*'
        WHEN (ll.COMMENTS IS NULL
            AND lu.comments IS NULL
            AND tl.COMMENTS IS NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) > 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) = 1
        THEN 'AADJ'
        WHEN (ll.COMMENTS IS NULL
            AND lu.comments IS NULL
            AND tl.COMMENTS IS NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) = 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) = 1
        THEN 'A'
        WHEN (ll.COMMENTS IS NOT NULL
            OR  lu.comments IS NOT NULL
            OR  tl.COMMENTS IS NOT NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) > 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) > 1
        THEN 'RADJ*'
        WHEN (ll.COMMENTS IS NOT NULL
            OR  lu.comments IS NOT NULL
            OR  tl.COMMENTS IS NOT NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) = 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) > 1
        THEN 'R*'
        WHEN (ll.COMMENTS IS NULL
            AND lu.comments IS NULL
            AND tl.COMMENTS IS NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) > 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) > 1
        THEN 'RADJ'
        WHEN (ll.COMMENTS IS NULL
            AND lu.comments IS NULL
            AND tl.COMMENTS IS NULL)
        AND NVL(ad.TOTALADJUSTMENT,0) = 0
        AND ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) > 1
        THEN 'R'
    END                                                                            AS status,
    ROW_NUMBER() OVER(PARTITION BY li.appliesToMonth ORDER BY li.creationdate ASC) AS Revision,
    COUNT(*) OVER(PARTITION BY li.appliesToMonth )                                 AS MaxRevision
FROM
    StudyArea s,
    Carrierrelationship c,
    STATE st,
    CARRIER cr,
    LOWINCOME li,
    LIFELINE ll,
    LINKUP lu,
    TOLLLIMITATION tl,
    (
        SELECT
            CASE
                WHEN X.TOTALADJUSTMENT = 1
                AND X.lifelineadjustment = 0
                AND X.linkupadjustment = 0
                AND X.tlsadjustment = 0
                THEN 0
                ELSE X.TOTALADJUSTMENT
            END AS TOTALADJUSTMENT ,
            X.LOWINCOMEID
        FROM
            (
                SELECT
                    A.LOWINCOMEID,
                    COUNT(*)                  AS TOTALADJUSTMENT,
                    SUM(A.lifelineadjustment) AS lifelineadjustment,
                    SUM(A.linkupadjustment)   AS linkupadjustment,
                    SUM(A.tlsadjustment)      AS tlsadjustment
                FROM
                    ADJUSTMENTS A
                GROUP BY
                    A.LOWINCOMEID) X) ad
WHERE
    c.STUDYAREAID = s.studyareaid
AND cr.CARRIERID = c.CARRIERID
AND s.STATEID = st.STATEID
AND li.CARRIERSTUDYAREAID = c.CARRIERSTUDYAREAID
AND ll.LOWINCOMEID = li.LOWINCOMEID
AND lu.LOWINCOMEID = li.LOWINCOMEID
AND tl.LOWINCOMEID (+) = li.LOWINCOMEID
AND ad.LOWINCOMEID (+) = li.LOWINCOMEID
AND (
        c.EFFECTIVE_THRU_DT IS NULL
    OR  c.EFFECTIVE_THRU_DT > SYSDATE)
AND spin = '143001454'
AND sac= '220355'