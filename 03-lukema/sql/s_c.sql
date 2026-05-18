
drop table emp;

create table emp
(
   id     number,
   name   varchar2(20),
   m_id   number,
   primary key(id)
);

insert into emp (id, name, m_id)
values 
(0, 'CEO', 0);

insert into emp (id, name, m_id)
values 
(1, 'Luke Ma', 0);

insert into emp (id, name, m_id)
values 
(2, 'Hong Lin', 1);

insert into emp (id, name, m_id)
values 
(3, 'Candice Ma', 0);

