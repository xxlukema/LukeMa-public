--
---------1.1
SELECT
    *
FROM
    T_CMSA_PI_ADVANCE
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
---------1.2
SELECT
    *
FROM
    T_CMSA_PI_RECOVERY
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
--
---------2.1
SELECT
    *
FROM
    T_CMSA_SERV_ADVANCE
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
---------2.2
SELECT
    *
FROM
    T_CMSA_SERV_RECOVERY
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
--
---------3.1
SELECT
    *
FROM
    T_CMSA_IOA_CALCULATION
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
---------
