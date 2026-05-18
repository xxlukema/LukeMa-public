set serveroutput on
DECLARE
    err_code INTEGER;
    err_msg VARCHAR2(200);
BEGIN
    FOR i IN 1..2
    LOOP
        DBMS_OUTPUT.put_line('Loop: ' || i);
        BEGIN
            INSERT INTO emp 
            VALUES (1, 'Luke Ma 2', trunc(sysdate));
        EXCEPTION
            WHEN OTHERS THEN
                err_code := SQLCODE;
                err_msg := substr(SQLERRM, 1, 200);
                INSERT INTO GLOBAL_TMP_ERROR
                values ('SQLERRM: ' || err_msg || '. SQLCODE: ' || err_code);
        END;
    END LOOP;
    
    FOR err_rec IN (SELECT MSG FROM GLOBAL_TMP_ERROR)
    LOOP 
            DBMS_OUTPUT.put_line('From DB: ' || err_rec.msg);
    END LOOP;
    
    DBMS_OUTPUT.put_line('Completed.');
    
    COMMIT;
END;
