SELECT
    ver.*,
    ' ' div,
    m.*
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
AND m.REPORT_ID = '33'
--AND ver.PO_NAME IS NOT NULL
AND (
        m.HAS_EBUY2_DATA = 'Y'
    OR  m.HAS_UMS_DATA = 'Y')
    --and ver.FACILITY_ID = '120440180'
    --AND ver.VERSION_ID != 1