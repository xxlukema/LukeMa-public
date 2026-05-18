explain analyze
SELECT
    SUM(salary) AS sum_salary,
    AVG(height) AS avg_height,
    lname
FROM
    aggregate_table
WHERE
    salary > 1000
GROUP BY
    lname
HAVING
    AVG(height) > 2;