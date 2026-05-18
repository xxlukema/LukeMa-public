explain --analyze
SELECT
    re.id,
    re.dtype,
    cur.ccuserid,
    cur.customergroup_id
FROM
    rootentity re,
    customeruserroot cur
WHERE
    re.id = cur.id
AND re.dtype='user';