
drop tablespace luke_data;
drop tablespace luke_index;

create tablespace luke_data
datafile '/db01/oracle/oradata/mail0/luke_data_01.dbf' size 50m
autoextend on
next 10m
maxsize 300m

create tablespace luke_index
datafile '/db02/oracle/oradata/mail0/luke_index_01.dbf' size 50m
autoextend on
next 10m
maxsize 300m

drop user luke;

create user luke identified by luke
default tablespace luke_data
temporary tablespace temp;

alter user luke quota 0 on system;
alter user luke quota unlimited on luke_data;
alter user luke quota unlimited on luke_index;

create role luke_role;

grant create session, create table, create procedure,
   create view
   to luke_role;

grant luke_role to luke;



