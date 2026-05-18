UPDATE
    T_CMSA_PI_ADVANCE
SET
    CREATE_DT = to_date('2012-08-08 18:33:33', 'yyyy-mm-dd hh24:mi:ss')
WHERE
    CREATE_DT IS NOT NULL
AND TRUNC(CREATE_DT ) = to_date('2012-08-08', 'yyyy-mm-dd');