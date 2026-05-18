-- Distinct
create table temp_luke 
(
   name    VARCHAR2(20),
   weight  NUMBER
);
--
insert into temp_luke 
(name, weight)
values
('luke', 10);
--
insert into temp_luke 
(name, weight)
values
('luke', 10);
--
insert into temp_luke 
(name, weight)
values
('luke', 20);
--
insert into temp_luke 
(name, weight)
values
('luke Ma', 10);
--
commit;
--
SELECT distinct name, WEIGHT FROM temp_luke;
--