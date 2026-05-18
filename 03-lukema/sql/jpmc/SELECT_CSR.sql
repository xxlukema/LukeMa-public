SELECT
    ID,
    DECODE(COUNT_N,1,SUBSTR(descr,1,INSTR(descr,'(',2)-1),DESCR) DESCR
FROM
    (
        SELECT DISTINCT
            (contact_record_id) ID,
            COUNT(1) OVER (PARTITION BY con.contact_sid) COUNT_N,
            (first_name || ' ' || last_name || '(' || SUBSTR(grp.group_name,12) || ')' ) descr
        FROM
            cs_contact_info con,
            cs_sec_user sec,
            cs_group grp
        WHERE
            con.contact_sid = sec.oracle_id
        AND con.status_code = 'A'
        AND con.contact_type = 'CSR'
        AND con.group_id = grp.group_id
    )
ORDER BY
    2