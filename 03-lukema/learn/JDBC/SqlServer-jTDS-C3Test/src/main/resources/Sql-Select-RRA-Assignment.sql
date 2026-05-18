SELECT
    c.CompanyName,
    a.DocTypeID,
    a.AssignDate,
    a.CreatedBy,
    a.ModifiedBy
FROM
    Assignment a,
    Company c
WHERE
    c.Companyid = a.BUID
AND a.DocTypeID = 2   /* FSA */
AND a.PropertyID = ?
AND a.CalendarID = ?