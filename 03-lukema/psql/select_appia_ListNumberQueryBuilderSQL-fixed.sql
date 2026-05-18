SELECT
    u.ccUserId,
    u.ccCallingLineFirstName,
    u.ccCallingLineLastName,
    u.extension,
    cg.ccGroupId,
    cg.site_id,
    cg.siteName,
    dn.ccPhoneNumber,
    dn.translationNumber,
    dn.directoryNumberType,
    ss.stationType,
    ss.rialtoStationType,
    ss.provisioningmode,
    ss.blockChanging,
    ss.user_info_required,
    ss.tollFree,
    d.macAddress,
    df.device_model,
    df.display,
    df.init_ports_num,
    e.port_label_1,
    e.port_label_2,
    e.port_label_3,
    CASE
        WHEN EXISTS
            (
                SELECT
                    svcname
                FROM
                    stationtype s
                INNER JOIN
                    stationtype_userservice stus
                ON
                    stus.stationtype_id = s.id
                INNER JOIN
                    userservice us
                ON
                    us.id = stus.services_id
                WHERE
                    us.svcname = 'ALTERNATE_NUMBERS'
                AND ss.stationtype = s.stationtype)
        THEN true
        ELSE false
    END AS alternateNumber,
    dl.userid,
    dl.emailaddress ,
    CASE
        WHEN ( ss.rialtoStationType = 'GROUP_PAGING' )
        THEN 0
        WHEN ( ss.rialtoStationType = 'TOLL_FREE' )
        THEN 1
        WHEN ( ss.rialtoStationType = 'ENHANCED_HUNT_GROUP' )
        THEN 2
        WHEN ( ss.rialtoStationType = 'AUTO_ATTENDANT' )
        THEN 3
        ELSE 99999
    END AS hskType
FROM
    CustomerUserroot u
INNER JOIN
    customergroup cg
ON
    u.customergroup_id = cg.id
LEFT JOIN
    customeruser cu
ON
    u.id = cu.id
LEFT JOIN
    dash_login dl
ON
    dl.customeruser_id = cu.id
LEFT JOIN
    stationtype ss
ON
    u.stationtype_id = ss.id
LEFT JOIN
    endpointaddress ea
ON
    u.id = ea.customeruser_id
LEFT JOIN
    endpoint e
ON
    e.id = ea.endpoint_id
LEFT JOIN
    customergroupaccessdevice d
ON
    d.id = e.customergroupaccessdevice_id
LEFT JOIN
    conf_device df
ON
    d.deviceconf_device_model = df.device_model
INNER JOIN
    DirectoryNumber dn
ON
    dn.customeruserroot_id = u.id
INNER JOIN
    customerenterprise en
ON
    en.id = cg.customerenterprise_id
WHERE
    en.ccserviceproviderid = 'ESADI22684'
AND (
        ea.dtype IS NULL
    OR  ea.dtype = 'PRIMARY')
ORDER BY
    hskType ASC NULLS FIRST