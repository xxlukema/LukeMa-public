UPDATE
    T_CMSA_SERV_RECOVERY
SET
    CREATE_DT = to_date('2013-01-09 20:49:44', 'yyyy-mm-dd hh24:mi:ss')
WHERE
    TO_CHAR(CREATE_DT,'yyyy-MM-dd') = '2013-01-10'