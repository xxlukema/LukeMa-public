--
CREATE OR REPLACE
PROCEDURE p_putline_luke
IS
  sys_date DATE;
BEGIN
  SELECT SYSDATE INTO sys_date FROM dual;
  DBMS_OUTPUT.put_line('Date proc: ' || sys_date);
END p_putline_luke;
/
show error;
--
CREATE OR REPLACE
  FUNCTION f_select_emp(
      in_id NUMBER := 2)
    RETURN emp%rowtype
  IS
    emp_row emp%rowtype;
  BEGIN
  DBMS_OUTPUT.put_line('In id: ' || in_id);
  SELECT * INTO emp_row FROM emp WHERE id = in_id;
  DBMS_OUTPUT.put_line('emp id: ' || emp_row.id);
  DBMS_OUTPUT.put_line('emp name: ' || emp_row.name);
  DBMS_OUTPUT.put_line('emp birth_date: ' || emp_row.birth_date);    RETURN emp_row;
  END f_select_emp;
  /
  show error; 