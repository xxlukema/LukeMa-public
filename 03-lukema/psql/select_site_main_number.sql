SELECT
    *
FROM
    rm_qa_sa.service_profile_phones
WHERE
    tn_id IS NULL
OR  LENGTH(LTrim(RTRIM(tn_id))) = 0