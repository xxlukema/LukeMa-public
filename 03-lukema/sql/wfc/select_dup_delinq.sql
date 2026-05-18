SELECT
    lp.loan_id,
    count( distinct p.COMMENTS)
FROM
    T_CMSA_PROPERTY_STATUS P ,
    T_CMSA_LOAN_PROPERTY LP
WHERE
    lp.property_id = p.property_id
AND p.property_status_cd = 2
AND RTRIM(p.comments) IS NOT NULL
--AND lp.loan_id = 600871921
group by lp.loan_id
having count( distinct p.COMMENTS) > 1;