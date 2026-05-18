SELECT
    *
FROM
    T_CMSA_DAILY_LOAN_FEED
WHERE
    (
        loan_id = 110202647
    OR  loan_id = 850201188)
AND FEED_DT = to_date('2012-04-30', 'yyyy-mm-dd');