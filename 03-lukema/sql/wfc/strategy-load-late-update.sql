--
---------1.1
UPDATE
    T_CMSA_PI_ADVANCE
SET
    CREATE_DT = to_date('2013-01-09 20:49:44', 'yyyy-mm-dd hh24:mi:ss')
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
---------1.2
UPDATE
    T_CMSA_PI_RECOVERY
SET
    CREATE_DT = to_date('2013-01-09 20:49:44', 'yyyy-mm-dd hh24:mi:ss')
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
--
---------2.1
UPDATE
    T_CMSA_SERV_ADVANCE
SET
    CREATE_DT = to_date('2013-01-09 20:49:44', 'yyyy-mm-dd hh24:mi:ss')
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
---------2.2
UPDATE
    T_CMSA_SERV_RECOVERY
SET
    CREATE_DT = to_date('2013-01-09 20:49:44', 'yyyy-mm-dd hh24:mi:ss')
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
--
---------3.1
UPDATE
    T_CMSA_IOA_CALCULATION
SET
    CREATE_DT = to_date('2013-01-09 20:49:44', 'yyyy-mm-dd hh24:mi:ss')
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10';
--    