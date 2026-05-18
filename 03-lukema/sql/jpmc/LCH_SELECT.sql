
SELECT
    *
FROM
    cs_ch_ia
WHERE
    cob_date = '23-May-2011'
AND source_feed_name IN ('LCHEMEAIA','LCHEMEAMTM','LCHEMEAFRA','LCHEMEAGN','LCHEMEAIAJPMSL',
    'LCHEMEAMTMJPMSL');



SELECT
    *
FROM
    cs_ch_mtm
WHERE
    cob_date = '23-May-2011'
AND source_feed_name IN ('LCHEMEAIA','LCHEMEAMTM','LCHEMEAFRA','LCHEMEAGN','LCHEMEAIAJPMSL',
    'LCHEMEAMTMJPMSL');


/*
SELECT
    *
FROM
    cs_stg_lch_ia
WHERE
    cob_date = '23-May-2011'
AND source_feed_name IN ('LCHEMEAIA','LCHEMEAMTM','LCHEMEAFRA','LCHEMEAGN','LCHEMEAIAJPMSL',
    'LCHEMEAMTMJPMSL');
*/

/*
SELECT
    *
FROM
    cs_stg_lch_gn
WHERE
    cob_date = '23-May-2011'
AND source_feed_name IN ('LCHEMEAIA','LCHEMEAMTM','LCHEMEAFRA','LCHEMEAGN','LCHEMEAIAJPMSL',
    'LCHEMEAMTMJPMSL');
*/

/*
SELECT
    *
FROM
    cs_stg_lch_mtm
WHERE
    cob_date = '23-May-2011'
AND source_feed_name IN ('LCHEMEAIA','LCHEMEAMTM','LCHEMEAFRA','LCHEMEAGN','LCHEMEAIAJPMSL',
    'LCHEMEAMTMJPMSL');
*/




