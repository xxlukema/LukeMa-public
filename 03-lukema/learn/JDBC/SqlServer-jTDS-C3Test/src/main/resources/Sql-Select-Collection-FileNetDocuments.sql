SELECT
    FSRequestedItem,
    FSImageDate,
    RRRequestedItem,
    RRImageDate,
    OSARImageDate
FROM
    FileNetDocuments
WHERE
    PropertyID = ?
AND CalendarID = ?