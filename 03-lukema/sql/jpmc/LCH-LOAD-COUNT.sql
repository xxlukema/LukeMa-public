
SELECT
    cob_date ,
    source_feed_name ,
    COUNT (*)
FROM
    cs_stg_lch_ia
WHERE
    cob_date = '23-May-2011'
GROUP BY
    cob_date ,
    source_feed_name

UNION

SELECT
    cob_date ,
    source_feed_name ,
    COUNT (*)
FROM
    cs_stg_lch_gn
WHERE
    cob_date = '23-May-2011'
GROUP BY
    cob_date ,
    source_feed_name

UNION

SELECT
    cob_date ,
    source_feed_name ,
    COUNT (*)
FROM
    cs_stg_lch_mtm
WHERE
    cob_date = '23-May-2011'
GROUP BY
    cob_date ,
    source_feed_name;


