SELECT
    ver.FACILITY_ID || ' ' || ver.FACILITY_NAME || ' ' || ver.ADDRESS || ' ' || ver.CITY || ' ' ||
    SUBSTR(ver.ZIP, 0, 5 ) AS LABEL,
    ver.FACILITY_ID,
    ver.FACILITY_NAME,
    ver.ADDRESS,
    ver.CITY,
    ver.STATE,
    SUBSTR(ver.ZIP, 0, 5 ) AS ZIP
FROM
    EUC_FACILITY_VERSION_T ver
JOIN
    EUC_REPORT_MATCH_T m
ON
    (
        m.FACILITY_ID = ver.FACILITY_ID
    AND m.VERSION_ID = ver.VERSION_ID)
WHERE
    ver.FACILITY_STATUS_CODE = 'A'
AND ver.PO_NAME IS NOT NULL
AND (
        m.HAS_EBUY2_DATA = 'Y'
    OR  m.HAS_UMS_DATA = 'Y')
AND m.REPORT_ID = #{REPORT_ID}
AND (
        UPPER(ver.FACILITY_ID) LIKE #{term}
    OR  UPPER(ver.FACILITY_NAME) LIKE #{term}
    OR  UPPER(ver.ADDRESS) LIKE #{term}
    OR  UPPER(ver.CITY) LIKE #{term}
    OR  SUBSTR(ver.ZIP, 0, 5 ) = #{term} )
ORDER BY
    ver.FACILITY_ID