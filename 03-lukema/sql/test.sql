create table witsmltest.Cs_Axis_Definition (oid number(19,0) not null,
name varchar2(64 char),
propertyType varchar2(64 char),
uid_witsml varchar2(64 char),
uom varchar2(24 char),
count number(5,0),
order number(5,0),
doubleValues raw(255),
stringValues raw(255),
primary key (oid))
;
/

