--
SELECT
    d.NAME AS department,
    e.name AS employee,
    e.salary
FROM
    department AS d
JOIN
    employee AS e
ON
    d.id = e.departmentid
WHERE
    (
        e.salary,
        e.departmentid)
    IN
    (   SELECT
            MAX(salary) AS salary,
            departmentid
        FROM
            employee
        GROUP BY
            departmentid )