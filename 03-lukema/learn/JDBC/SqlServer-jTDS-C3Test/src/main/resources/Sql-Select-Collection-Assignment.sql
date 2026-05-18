SELECT
    a.BUID,
    c.CompanyName,
    a.IsPriority,
    a.DocTypeID,
    a.AssignDate,
    a.CreatedBy,
    a.ModifiedBy
FROM
    Assignment a ,
    Company c
WHERE
    c.Companyid = a.BUID
AND a.DocTypeID = 1   /* RRA */
AND a.PropertyID = ?
AND a.CalendarID = ?