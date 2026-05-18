--
--
--
CREATE TABLE tmp_emp
  (
    id   NUMBER ( 32 ) PRIMARY KEY,
    name VARCHAR2 ( 20 ) ,
    age  NUMBER ,
    birth_date DATE
  );
--
--
--
INSERT INTO tmp_emp (id, name, age, birth_date)
values (1, 'Luke Ma', 10, to_date('31-aug-2004', 'dd-mon-yyyy'));
--
INSERT INTO tmp_emp (id, name, age, birth_date)
values (2, 'John Doe', 11, to_date('01-may-1979', 'dd-mon-yyyy'));
--
INSERT INTO tmp_emp (id, name, age, birth_date)
values (3, 'Adam Smith', 12, to_date('02-aug-2000', 'dd-mon-yyyy'));
--
commit;


