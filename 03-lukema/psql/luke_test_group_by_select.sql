SELECT
    nextval('seq_luke_test'),
    lname,
    fname,
    item,
    SUM(sales) as "total sales"
FROM
    luke_test
GROUP BY
    lname,
    fname,
    item;