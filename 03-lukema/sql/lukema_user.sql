
create user luke identified by luke
default tablespace users
temporary tablespace temp;

alter user luke quota 0 on system;
alter user luke quota unlimited on users;

create role luke_role;

grant create session, create table, create procedure,
   create view
   to luke_role;

grant luke_role to luke;



