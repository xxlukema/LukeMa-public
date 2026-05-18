SELECT
    s.SAC,
    s.NAME AS sacName,
    cr.SPIN,
    cr.NAME                                  AS spinName,
    TO_CHAR(p.enteredMonth, 'FMMonth, YYYY') AS appliesToMonth,
    TO_CHAR(p.enteredMonth, 'FMMonth, YYYY') AS enteredMonth,
    CASE
        WHEN (p.OVERRIDEFLAG = 0
            AND p.COMMENTS IS NOT NULL)
        THEN 'P*'
        WHEN (p.OVERRIDEFLAG = 0
            AND p.COMMENTS IS NULL)
        THEN 'P'
        WHEN (p.OVERRIDEFLAG = 1
            AND p.COMMENTS IS NOT NULL)
        THEN 'O*'
        WHEN (p.OVERRIDEFLAG = 1
            AND p.COMMENTS IS NULL)
        THEN 'O'
    END                                                                        AS status,
    ROW_NUMBER() OVER(PARTITION BY p.enteredMonth ORDER BY p.creationdate ASC) AS Revision,
    COUNT(*) OVER(PARTITION BY p.enteredMonth )                                AS MaxRevision
FROM
    StudyArea s,
    Carrierrelationship c,
    STATE st,
    CARRIER cr,
    PROJECTION p,
    PROJECTIONREASON pr
WHERE
    c.STUDYAREAID = s.studyareaid
AND cr.CARRIERID = c.CARRIERID
AND s.STATEID = st.STATEID
AND p.CARRIERSTUDYAREAID = c.CARRIERSTUDYAREAID
AND p.PROJECTIONREASONID = pr.PROJECTIONREASONID(+)
AND (
        c.EFFECTIVE_THRU_DT IS NULL
    OR  c.EFFECTIVE_THRU_DT > SYSDATE)
AND spin = '143001454'
AND sac= '220355'