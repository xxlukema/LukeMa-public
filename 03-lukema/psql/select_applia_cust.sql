SELECT
    re.id,
    cur.ccuserid,
    cg.ccgroupid,
    ce.ccserviceproviderid,
    ce.ccserviceprovidername,
    ce.customerid,
    cur.ccuserid
FROM
    customeruser cu
JOIN
    rootentity re
ON
    re.id = cu.id
JOIN
    customeruserroot cur
ON
    cur.id = re.id
JOIN
    customergroup cg
ON
    cg.id = cur.customergroup_id
JOIN
    customerenterprise ce
ON
    ce.id = cg.customerenterprise_id
WHERE
    re.dtype = 'user'
AND ce.ccserviceproviderid = 'ESADI14820'
AND cg.ccgroupid = 'ESADI14820L14940'
AND cur.ccuserid = '3422981084@sboxint.adpt-tech.com'
ORDER BY
    2;