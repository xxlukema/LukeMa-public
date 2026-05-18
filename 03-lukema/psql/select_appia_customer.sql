SELECT
    re.id,
    re.dtype,
    cur.ccuserid,
    cur.customergroup_id,
    ccphonenumber
FROM
    rootentity re,
    customeruserroot cur,
    customeruser cu,
    directorynumber dn
WHERE
    re.id = cur.id
AND re.id = cu.id
AND dn.customeruserroot_id = re.id;