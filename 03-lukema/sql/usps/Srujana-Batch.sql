SELECT
    TO_CHAR(ps_sample_date, 'yyyy-mm-dd:hh24:mi:ss')
FROM
    eems.point_samples
WHERE
    ps_sample_id =
    (
        SELECT
            MAX(ps_sample_id)
        FROM
            eems.point_samples);