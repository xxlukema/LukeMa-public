explain
SELECT
    ce.ccserviceprovidername,
    cg.ccgroupid,
    dn.ccphonenumber
FROM
    customerenterprise ce
JOIN
    customergroup cg
ON
    cg.customerenterprise_id = ce.id
JOIN
    directorynumber dn
ON
    dn.customergroup_id = cg.id
WHERE
    cg.id = '1107830'