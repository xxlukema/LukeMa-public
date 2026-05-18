
--DROP TABLE OPS$COAST.TMP_LUKE 

/*
create table OPS$COAST.TMP_LUKE
(
   INTEREST_AMOUNT   number(38, 5) default 0
)
*/

/*
insert into OPS$COAST.TMP_LUKE 
(
   INTEREST_AMOUNT
)
values
(
    1000.2
)
*/

/*
insert into OPS$COAST.TMP_LUKE 
(
   INTEREST_AMOUNT
)
values
(
    -2000.4
)
*/

/*
insert into OPS$COAST.TMP_LUKE 
(
   INTEREST_AMOUNT_2, COMPOUND_IND
)
values
(
    200.3, 'Y'
)
*/

/*
insert into OPS$COAST.TMP_LUKE 
(
   INTEREST_AMOUNT
)
values
(
    300.4
)
*/

--ALTER TABLE OPS$COAST.TMP_LUKE RENAME COLUMN interest_amount TO liab_dly_int;

--ALTER TABLE OPS$COAST.TMP_LUKE ADD (asset_dly_int   NUMBER(38, 5) DEFAULT 0);

--ALTER TABLE OPS$COAST.TMP_LUKE ADD (compound_ind   CHAR(1) DEFAULT 'N');

--ALTER TABLE OPS$COAST.TMP_LUKE ADD CONSTRAINT check_compound_ind CHECK (compound_ind IN ('N', 'Y'));



/*
update TMP_LUKE
set asset_dly_int = liab_dly_int,
    liab_dly_int = 0
where liab_dly_int > 0;
*/

/*
update TMP_LUKE
set liab_dly_int = (-1) * liab_dly_int
where liab_dly_int < 0;
*/


--update TMP_LUKE set compound_ind = 'Y' where liab_dly_int = 200.4

--select * from TMP_LUKE 

--select sum(liab_dly_int), asset_dly_int, COMPOUND_IND from TMP_LUKE group by asset_dly_int, COMPOUND_IND


select * from TMP_LUKE

--alter table OPS$COAST.TMP_LUKE MODIFY asset_dly_int DEFAULT 2.6;

/*
insert into OPS$COAST.TMP_LUKE
(liab_dly_int)
VALUES
(111.4);
*/
