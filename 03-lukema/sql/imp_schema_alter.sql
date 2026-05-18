
ALTER TABLE OA_AFC DROP 
   Constraint PK_OA_AFC;

ALTER TABLE OA_AFC DROP 
   Constraint OA_AFC_PK;

ALTER TABLE OA_CAT_ASSOC DROP 
   Constraint OA_CAT_ASSOC_PK;

DROP INDEX OA_SE_ASSOC_IND;

ALTER TABLE OA_AFC ADD 
   Constraint OA_AFC_PK Primary Key (market, REF, mnemonic);

ALTER TABLE OA_CAT_ASSOC ADD 
   Constraint OA_CAT_ASSOC_PK Primary Key (market, REF, assoc_ref);

CREATE INDEX OA_SE_ASSOC_IND ON OA_SE_ASSOC(REF);

