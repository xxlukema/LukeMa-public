explain SELECT
    t2.ID                      AS a1,
    t2.DTYPE                   AS a2,
    t2.STATUS                  AS a3,
    t2.VERSION                 AS a4,
    t3.ID                      AS a5,
    t3.bypass_vpn              AS a6,
    t3.MACADDRESS              AS a7,
    t3.UNREMOVABLE             AS a8,
    t3.vlanTagOverride         AS a9,
    t3.CUSTOMERGROUP_ID        AS a10,
    t3.DEVICECONF_DEVICE_MODEL AS a11
FROM
    customerenterprise t6,
    ROOTENTITY t5,
    conf_device t4,
    CUSTOMERGROUPACCESSDEVICE t3,
    ROOTENTITY t2,
    CUSTOMERGROUP t1,
    ROOTENTITY t0
WHERE
    ((((((
                            t6.CCSERVICEPROVIDERID = 'ESADI14820')
                    AND (
                            t3.DEVICECONF_DEVICE_MODEL <> 'CommunicatorBW'))
                AND (
                        t3.DEVICECONF_DEVICE_MODEL <> 'CommunicatorPC'))
            AND (
                    t3.DEVICECONF_DEVICE_MODEL <> 'CommunicatorTB'))
        AND ((
                    t3.ID = t2.ID)
            AND (
                    t2.DTYPE = 'device')))
    AND ((((
                        t0.ID = t3.CUSTOMERGROUP_ID)
                AND ((
                            t1.ID = t0.ID)
                    AND (
                            t0.DTYPE = 'CustomerGroup')))
            AND ((
                        t5.ID = t1.CUSTOMERENTERPRISE_ID)
                AND ((
                            t6.ID = t5.ID)
                    AND (
                            t5.DTYPE = 'CustomerEnterprise'))))
        AND (
                t4.DEVICE_MODEL = t3.DEVICECONF_DEVICE_MODEL))); --LIMIT 200 OFFSET 0;
