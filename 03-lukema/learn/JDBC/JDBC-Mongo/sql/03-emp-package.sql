--
-- Header
--
CREATE OR REPLACE PACKAGE tmp_emp_package IS
  --
  TYPE TYPE_RC_EMP IS REF CURSOR RETURN tmp_emp%ROWTYPE;
  --
  PROCEDURE putline;
  --
  FUNCTION select_emp_f(in_id NUMBER := 2) RETURN tmp_emp%ROWTYPE;
  --
  PROCEDURE select_emp(in_id NUMBER := 2, out_rc_emp OUT TYPE_RC_EMP);
	  
	
END tmp_emp_package;
  /
  show error;
  --
  -- Body
  --
CREATE OR REPLACE PACKAGE BODY tmp_emp_package IS
	--
	PROCEDURE putline IS
	  sys_date DATE;
	  err_code INTEGER := 0;
	  err_msg  VARCHAR2(200);
	BEGIN
	  DBMS_OUTPUT.put_line('Begin putline.');
	  SELECT SYSDATE INTO sys_date FROM dual;
	  DBMS_OUTPUT.put_line('Date package: ' || sys_date);
	  DBMS_OUTPUT.put_line('End putline.');
	EXCEPTION
	  WHEN OTHERS THEN
	     err_code := SQLCODE;
	     err_msg := SUBSTR(SQLERRM, 1, 200);
	     DBMS_OUTPUT.put_line('EXCEPTION: err_code = ' || err_code);
	     DBMS_OUTPUT.put_line('EXCEPTION: err_msg = ' || err_msg);
	END putline;
	--
	FUNCTION select_emp_f(in_id NUMBER := 2) RETURN tmp_emp%ROWTYPE IS
	  emp_row tmp_emp%ROWTYPE;
	BEGIN
	  DBMS_OUTPUT.put_line('In id: ' || in_id);
	  SELECT * INTO emp_row FROM tmp_emp WHERE id = in_id;
	  DBMS_OUTPUT.put_line('emp id: ' || emp_row.id);
	  DBMS_OUTPUT.put_line('emp name: ' || emp_row.name);
	  DBMS_OUTPUT.put_line('emp birth_date: ' || emp_row.birth_date);
	  RETURN emp_row;
	END select_emp_f;

	PROCEDURE select_emp(in_id IN NUMBER := 2, out_rc_emp OUT TYPE_RC_EMP) IS
	BEGIN
	  DBMS_OUTPUT.put_line('select_emp in id: ' || in_id);
	  OPEN out_rc_emp FOR SELECT * FROM tmp_emp where id >= in_id order by id;
	  DBMS_OUTPUT.put_line('select_emp completed.');
	END select_emp;

END emp_package;
/
show error;
--
CREATE OR REPLACE SYNONYM emp_pkg FOR emp_package;
CREATE OR REPLACE PUBLIC SYNONYM emp_pkg FOR emp_package;
