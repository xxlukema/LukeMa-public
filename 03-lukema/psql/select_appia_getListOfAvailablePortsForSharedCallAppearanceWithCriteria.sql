explain analyze
SELECT
    t1.MACADDRESS,
    t3.DEVICE_MODEL,
    t2.PHYPORT,
    t4.CCPHONENUMBER,
    t6.CCCALLINGLINELASTNAME,
    t6.CCCALLINGLINEFIRSTNAME,
    t8.LINEPORT,
    CASE
        WHEN (t6.CCUSERID = '3422981084@sboxint.adpt-tech.com')
        THEN true
        ELSE false
    END
FROM
    endpoint t2
LEFT OUTER JOIN
    ENDPOINTADDRESS t8
ON
    (
        t8.ENDPOINT_ID = t2.ID)
LEFT OUTER JOIN
    (ROOTENTITY t5
JOIN
    CUSTOMERUSERROOT t6
ON
    (
        t6.ID = t5.ID)
JOIN
    CUSTOMERUSER t7
ON
    (
        t7.ID = t5.ID))
ON
    (
        t5.ID = t8.CUSTOMERUSER_ID)
LEFT OUTER JOIN
    DIRECTORYNUMBER t4
ON
    (
        t4.CUSTOMERUSERROOT_ID = t5.ID),
    CUSTOMERGROUP t10,
    ROOTENTITY t9,
    conf_device t3,
    CUSTOMERGROUPACCESSDEVICE t1,
    ROOTENTITY t0
WHERE
    (((((((((
                                        t3.DEVICE_CATEGORY = 'IP Phone')
                                AND (
                                        t3.SOFT_CLIENT = false))
                            AND (
                                    t10.ccgroupid = 'ESADI14820L14940'))
                        AND (((
                                        t6.CCUSERID IS NULL)
                                OR  ((
                                            t6.CCUSERID <> '3422981084@sboxint.adpt-tech.com')
                                    AND (
                                            t8.LOGICALPORTNUM = '1')))
                            OR  (
                                    t6.CCUSERID = '3422981084@sboxint.adpt-tech.com')))
                    AND t2.ID NOT IN
                                      (
                                      SELECT DISTINCT
                                          t11.ID
                                      FROM
                                          endpoint t11,
                                          CUSTOMERUSER t19,
                                          CUSTOMERUSERROOT t18,
                                          ROOTENTITY t17,
                                          ENDPOINTADDRESS t16,
                                          CUSTOMERGROUP t15,
                                          ROOTENTITY t14,
                                          CUSTOMERGROUPACCESSDEVICE t13,
                                          ROOTENTITY t12
                                      WHERE
                                          (((
                                                      t15.ccgroupid = 'ESADI14820L14940')
                                              AND (
                                                      t18.CCUSERID =
                                                      '3422981084@sboxint.adpt-tech.com'))
                                          AND ((((((
                                                                      t12.ID =
                                                                      t11.CUSTOMERGROUPACCESSDEVICE_ID
                                                                  )
                                                              AND ((
                                                                          t13.ID = t12.ID)
                                                                  AND (
                                                                          t12.DTYPE = 'device')))
                                                          AND ((
                                                                      t14.ID = t13.CUSTOMERGROUP_ID
                                                                  )
                                                              AND ((
                                                                          t15.ID = t14.ID)
                                                                  AND (
                                                                          t14.DTYPE =
                                                                          'CustomerGroup'))))
                                                      AND (
                                                              t16.ENDPOINT_ID = t11.ID))
                                                  AND (
                                                          t16.DTYPE = 'PRIMARY'))
                                              AND ((
                                                          t17.ID = t16.CUSTOMERUSER_ID)
                                                  AND (((
                                                                  t19.ID = t17.ID)
                                                          AND ((
                                                                      t18.ID = t17.ID)
                                                              AND (
                                                                      t18.ID = t17.ID)))
                                                      AND (
                                                              t17.DTYPE = 'user')))))))
                AND t2.ID NOT IN
                                  (
                                  SELECT DISTINCT
                                      t20.ID
                                  FROM
                                      ENDPOINTADDRESS t26
                                  LEFT OUTER JOIN
                                      (ROOTENTITY t27
                                  JOIN
                                      CUSTOMERUSERROOT t28
                                  ON
                                      (
                                          t28.ID = t27.ID)
                                  JOIN
                                      CUSTOMERUSER t29
                                  ON
                                      (
                                          t29.ID = t27.ID))
                                  ON
                                      (
                                          t27.ID = t26.CUSTOMERUSER_ID),
                                      conf_device t25,
                                      CUSTOMERGROUP t24,
                                      ROOTENTITY t23,
                                      CUSTOMERGROUPACCESSDEVICE t22,
                                      ROOTENTITY t21,
                                      endpoint t20
                                  WHERE
                                      ((((
                                                      t28.CCUSERID <>
                                                      '3422981084@sboxint.adpt-tech.com')
                                              AND (
                                                      t24.ccgroupid = 'ESADI14820L14940'))
                                          AND (
                                                  t25.SOFT_CLIENT = false))
                                      AND ((((
                                                          t26.ENDPOINT_ID = t20.ID)
                                                  AND ((
                                                              t21.ID =
                                                              t20.CUSTOMERGROUPACCESSDEVICE_ID)
                                                      AND ((
                                                                  t22.ID =t21.ID)
                                                          AND (
                                                                  t21.DTYPE = 'device'))))
                                              AND ((
                                                          t23.ID = t22.CUSTOMERGROUP_ID)
                                                  AND ((
                                                              t24.ID = t23.ID)
                                                      AND (
                                                              t23.DTYPE = 'CustomerGroup'))))
                                          AND (
                                                  t25.DEVICE_MODEL = t22.DEVICECONF_DEVICE_MODEL)))
                                  GROUP BY
                                      t20.ID,
                                      t25.MAX_PORTS_NUM
                                  HAVING
                                      (
                                          t25.MAX_PORTS_NUM <= SUM(t26.lineweight))))
            AND t6.CCCALLINGLINEFIRSTNAME LIKE '%.%')
        AND t6.CCCALLINGLINELASTNAME LIKE '%2%')
    AND ((((
                        t0.ID = t2.CUSTOMERGROUPACCESSDEVICE_ID)
                AND ((
                            t1.ID = t0.ID)
                    AND (
                            t0.DTYPE = 'device')))
            AND (
                    t3.DEVICE_MODEL = t1.DEVICECONF_DEVICE_MODEL))
        AND ((
                    t9.ID = t1.CUSTOMERGROUP_ID)
            AND ((
                        t10.ID = t9.ID)
                AND (
                        t9.DTYPE = 'CustomerGroup')))));