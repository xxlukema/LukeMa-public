--1. Merge
MERGE
INTO
    T_CMSA_PERIODIC_LOAN target
USING
    T_CMSA_PERIODIC_LOAN_BAK source
ON
    (
        target.LOAN_ID = source.LOAN_ID
    AND target.PERIODIC_YEAR = source.PERIODIC_YEAR
    AND target.PERIODIC_MONTH = source.PERIODIC_MONTH)
WHEN MATCHED
    THEN
UPDATE
SET
    target.DELINQUENT_COMMENTS = source.DELINQUENT_COMMENTS_OLD;
--2. Commit
COMMIT;
