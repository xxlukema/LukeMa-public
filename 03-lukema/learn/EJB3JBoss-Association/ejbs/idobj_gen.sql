
/*
select top 100 s.*, sl.* 
from Swap s, SwapLeg sl
where s.fiId = sl.parentId
and s.swapNum = 998877
order by s.fiId desc, sl.legId desc
*/

--select * from Swap where name = 'name'
--select * from SwapLeg where fxResetSource like 'fxResetSource%'

--select top 10 * from  Swap order by enterTime desc
--select top 10 * from  SwapLeg order by enterTime desc

--delete from SwapLeg where fxResetSource like 'fxResetSource%'
--delete from Swap where name = 'name'


--select top 10 * from Swap order by fiId desc

-- select * from id_obj_gen_tmp;

--alter table id_obj_gen_tmp modify type varchar(12) not null
--delete from id_obj_gen_tmp where type is null

--select * from SwapLeg where parentId = 87195

--select * from Luke_Test

--select * into id_obj_gen_tmp from idobj_gen

--select * from id_obj_gen_tmp

--alter table id_obj_gen_tmp 
--modify type varchar(12)

--delete from Auto_Parent where id = 87194

/*
alter table id_obj_gen_tmp
add str_type    varchar(12)   null;
*/

/*
update id_obj_gen_tmp
set str_type = 'Swap'
where type = '529'
*/

/*
update id_obj_gen_tmp
set str_type = 'SwapLeg'
where type = '530'
*/

/*
update id_obj_gen_tmp
set str_type = 'default'
where type != '530' and type != '529'
*/

/*
--Sybase
create view idobj_gen_view
as
select cast(type as varchar(12)) type, avail_id
from idobj_gen
*/

--drop view idobj_gen_view

--select * from idobj_gen_view

--select * from id_obj_gen_tmp

--select top 10 * from SwapLeg order by fiId desc
--select top 10 * from Swap order by fiId desc

--update idobj_gen_view set avail_id = avail_id + 1 where type = '529'

/*
create table idobj_gen
as
select cast(type as UNSIGNED) type, avail_id
from idobj_gen_view
*/

--select * from idobj_gen

--desc idobj_gen

--drop table idobj_gen_view

/*
--MySQL
create view idobj_gen_view
as
select cast(type as char(12)) type, avail_id
from idobj_gen
*/

--select * from idobj_gen_view

--drop view idobj_gen_view

/*
create table idobj_gen_tmp
as
select cast(type as UNSIGNED) type, avail_id
from idobj_gen
*/

--drop table idobj_gen

/*
create table idobj_gen
as
select type, avail_id
from idobj_gen_tmp
*/

