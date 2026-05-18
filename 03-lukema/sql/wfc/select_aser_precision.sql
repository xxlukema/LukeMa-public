SELECT
    LOAN_ID,
    ASER_PERCNT,
    EFFECTIVE_DT,
    CREATE_DT,
    MODIFY_DT
FROM
    T_CMSA_LOAN_ASER
WHERE
    ASER_PERCNT > 0
AND LOAN_ID IN (600876274,
                600876277,
                700401584,
                700401627,
                710205084,
                710205097,
                710205102 )
AND EFFECTIVE_DT = to_date('2012-04-09', 'yyyy-mm-dd')
ORDER BY
    EFFECTIVE_DT DESC;