create table luke
(
  ssn     char(9),
  lname   varchar2(15),
  fname   varchar2(15),
  age     number(3)
)
/


insert into luke
(ssn, lname, fname, age)
values
('111-11-1111', 'Ma', 'Luke', 28);

insert into luke
(ssn, lname, fname, age)
values
('111-11-1112', 'Lin', 'Hong', 24);


