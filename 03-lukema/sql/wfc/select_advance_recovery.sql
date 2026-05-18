SELECT
    *
FROM
    T_CMSA_TI_ADVANCE
WHERE
    ADVANCE_DT >= to_date('2012-04-30', 'yyyy-mm-dd');
--
SELECT
    *
FROM
    T_CMSA_TI_RECOVERY
WHERE
    RECOVERY_DT >= to_date('2012-04-30', 'yyyy-mm-dd');
    