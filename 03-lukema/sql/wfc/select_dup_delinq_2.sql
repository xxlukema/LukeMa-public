SELECT
    p.*,
    lp.*
FROM
    T_CMSA_PROPERTY_STATUS P ,
    T_CMSA_LOAN_PROPERTY LP
WHERE
    lp.property_id = p.property_id
AND p.property_status_cd = 2
AND RTRIM(p.comments) IS NOT NULL
AND lp.loan_id IN ( 600871921,
                   700400394)
ORDER BY
    1, 3