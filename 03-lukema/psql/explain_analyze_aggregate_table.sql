explain analyze
SELECT
    (salary) AS sum_salary,
    (height) AS avg_height,
    lname
FROM
    aggregate_table
WHERE
    salary > 1000