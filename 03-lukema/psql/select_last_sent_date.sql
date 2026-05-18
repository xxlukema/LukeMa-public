SELECT
    last_sent_date,
    transaction_status,
    response_date,
    *
FROM
    billing_transaction
WHERE
    last_sent_date IS NOT NULL
ORDER BY
    last_sent_date DESC nulls last limit 20;