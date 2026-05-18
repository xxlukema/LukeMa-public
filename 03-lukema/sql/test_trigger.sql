CREATE TABLE tmp_luke
  ( name VARCHAR2(20), weight NUMBER(10,2)
  );
--
/*
CREATE OR REPLACE TRIGGER tmp_luke BEFORE
UPDATE OR
INSERT ON tmp_luke FOR EACH ROW DECLARE v_name tmp_luke.name%TYPE;
BEGIN
DBMS_OUTPUT.put_line('OLD.name: ' || :OLD.name);
DBMS_OUTPUT.put_line('NEW.name: ' || :NEW.name);
IF NVL (:OLD.name, '@#$#') != NVL (:NEW.name, '@#$#') THEN
BEGIN
DBMS_OUTPUT.put_line('NOT equal.');
:NEW.name := :NEW.name || '111';
EXCEPTION
WHEN OTHERS THEN
NULL;
END;
ELSE
DBMS_OUTPUT.put_line('Equal.');
:NEW.name := :NEW.name || '222';
END IF;
END;
*/
--
INSERT
INTO tmp_luke
  (
    name,
    weight
  )
  VALUES
  (
    'luke',
    100
  );
--
SELECT * FROM tmp_luke;
--
DELETE FROM tmp_luke;
--
SET serveroutput ON format wrapped;
--
UPDATE tmp_luke SET name = 'luke111', weight = 110;