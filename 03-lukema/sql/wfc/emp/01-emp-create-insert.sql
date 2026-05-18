--
--
--
CREATE TABLE emp
  (
    id   NUMBER ( 32 ) PRIMARY KEY,
    name VARCHAR2 ( 20 ) ,
    birth_date DATE
  );
--
--
--
INSERT INTO emp (id, name, birth_date)
values (1, 'Luke Ma', to_date('31-aug-2004','dd-mon-yyyy'));
--
INSERT INTO emp (id, name, birth_date)
values (2, 'John Doe', to_date('01-may-1979','dd-mon-yyyy'));
--
INSERT INTO emp (id, name, birth_date)
values (3, 'Adam Smith', to_date('02-aug-2000','dd-mon-yyyy'));
--
commit;


