explain analyze
SELECT
    d.dname,
    e.fname,
    e.lname
FROM
    dept d ,
    emp e
WHERE
    d.id = e.dept_id
AND e.fname = 'luke'
