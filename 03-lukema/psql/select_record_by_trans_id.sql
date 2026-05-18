SELECT
    *
FROM
    billing_record_transaction t
WHERE
    t.order_id = :oid
AND t.transaction_qualifier = :tqid;
