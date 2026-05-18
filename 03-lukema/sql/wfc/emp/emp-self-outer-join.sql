drop table emp;
--
CREATE TABLE
emp
(
id NUMBER(12) NOT NULL,
name VARCHAR2(24) NOT NULL,
manager_id NUMBER(12)
);

--

insert into emp (id, name, manager_id) values (1, 'Justin', 2);
insert into emp (id, name, manager_id) values (2, 'Ajit', null);
insert into emp (id, name, manager_id) values (3, 'Luke', 1);
insert into emp (id, name, manager_id) values (4, 'Tom', 0);

--

SELECT
a.name AS emp_name,
b.name AS mgr_name
FROM
emp a,
emp b
WHERE
a.manager_id = b.id(+);

--
SELECT
    a.name AS emp_name,
    b.name AS mgr_name
FROM
    emp a
LEFT OUTER JOIN
    emp b
ON
    a.manager_id = b.id;