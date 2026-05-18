SELECT
    lp.loan_id ,
    lp.property_seq_no ,
    lp.prospectus_property_id,
    p.*
FROM
    T_CMSA_PROPERTY_STATUS P ,
    T_CMSA_LOAN_PROPERTY LP
WHERE
    lp.property_id = p.property_id
--AND p.property_status_cd = 2
--AND RTRIM(p.comments) IS NOT NULL
and loan_id = 310900868