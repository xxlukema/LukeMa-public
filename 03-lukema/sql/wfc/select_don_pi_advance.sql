SELECT
    *
FROM
    t_cmsa_pi_advance
WHERE
    -- CREATE_DT = TO_DATE('2012-04-10','yyyy-mm-dd' )
    advance_dt=TO_DATE('2013-01-16','yyyy-mm-dd' )
ORDER BY
    investor_id