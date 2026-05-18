
UPDATE OPS$COAST.CS_INTSTMT_BALANCE
   SET asset_dly_int = liab_dly_int, 
       liab_dly_int = 0
 WHERE liab_dly_int > 0;

UPDATE OPS$COAST.CS_INTSTMT_BALANCE
   SET liab_dly_int = (-1) * liab_dly_int
 WHERE liab_dly_int < 0;
 
COMMIT;

