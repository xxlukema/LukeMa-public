SET serveroutput ON
DECLARE
  sys_date DATE;
BEGIN
  SELECT SYSDATE INTO sys_date FROM dual;
  DBMS_OUTPUT.put_line('Date: ' || sys_date);
END;