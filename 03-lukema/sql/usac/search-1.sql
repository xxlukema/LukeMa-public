SELECT
    s.SAC   AS sac,
    s.NAME  AS sacName,
    cr.SPIN AS spin,
    cr.NAME AS spinName
FROM
    StudyArea s,
    Carrierrelationship c,
    STATE st,
    CARRIER cr
WHERE
    c.STUDYAREAID = s.studyareaid
AND cr.CARRIERID = c.CARRIERID
AND s.STATEID = st.STATEID
AND (
        c.EFFECTIVE_THRU_DT IS NULL
    OR  c.EFFECTIVE_THRU_DT > SYSDATE)
AND sac ='220355'
ORDER BY
    spin