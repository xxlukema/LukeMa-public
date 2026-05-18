explain 
--analyze
SELECT
    *
FROM
    dept d,
    emp e
WHERE
    d.id = e.dept_id
AND e.ssn = 'ssn1';