--
-- Header
--
CREATE OR REPLACE PACKAGE emp_package IS
  --
  TYPE TYPE_RC_EMP IS REF CURSOR RETURN emp%ROWTYPE;
  --
  PROCEDURE putline;
  --
  FUNCTION select_emp_f(in_id NUMBER := 2) RETURN emp%ROWTYPE;
  --
  PROCEDURE select_emp(in_id NUMBER := 2, out_rc_emp OUT TYPE_RC_EMP);
	  
	
END emp_package;
  /
  show error;
  --
  -- Body
  --
CREATE OR REPLACE PACKAGE BODY emp_package IS
	--
	PROCEDURE putline IS
	  sys_date DATE;
	BEGIN
	  SELECT SYSDATE INTO sys_date FROM dual;
	  DBMS_OUTPUT.put_line('Date package: ' || sys_date);
	END putline;
	--
	FUNCTION select_emp_f(in_id NUMBER := 2) RETURN emp%ROWTYPE IS
	  emp_row emp%ROWTYPE;
	BEGIN
	  DBMS_OUTPUT.put_line('In id: ' || in_id);
	  SELECT * INTO emp_row FROM emp WHERE id = in_id;
	  DBMS_OUTPUT.put_line('emp id: ' || emp_row.id);
	  DBMS_OUTPUT.put_line('emp name: ' || emp_row.name);
	  DBMS_OUTPUT.put_line('emp birth_date: ' || emp_row.birth_date);
	  RETURN emp_row;
	END select_emp_f;

	PROCEDURE select_emp(in_id IN NUMBER := 2, out_rc_emp OUT TYPE_RC_EMP) IS
	BEGIN
	  DBMS_OUTPUT.put_line('select_emp in id: ' || in_id);
	  OPEN out_rc_emp FOR SELECT * FROM emp where id >= in_id order by id;
	  DBMS_OUTPUT.put_line('select_emp completed.');
	END select_emp;

END emp_package;
/
show error;
--
CREATE OR REPLACE SYNONYM emp_pkg FOR emp_package;
CREATE OR REPLACE PUBLIC SYNONYM emp_pkg FOR emp_package;
