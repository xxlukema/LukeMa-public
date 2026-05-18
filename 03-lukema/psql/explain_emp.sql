explain
SELECT
    e.*
FROM
    dept d
JOIN
    emp e
ON
    d.id = e.dept_id
AND e.ssn = 'ssn1';
