SELECT
    e1.Name AS Employee,
    e1.Salary
FROM
    Employee e1
WHERE
    3 >
    (   SELECT
            COUNT(DISTINCT e2.Salary)
        FROM
            Employee e2
        WHERE
            e2.Salary > e1.Salary );
--------------
-- HAVING
--------------
SELECT
    e1.salary,
    e1.NAME,
    COUNT(0) AS COUNT
FROM
    employee AS e1
JOIN
    employee AS e2
ON
    e1.salary > e2.salary
GROUP BY
    e1.salary,
    e1.name
HAVING
    COUNT(0) >= 3
ORDER BY
    e1.salary DESC;
--------------
-- max()
--------------
SELECT
    MAX(salary)
FROM
    employee
WHERE
    salary NOT IN
    (   SELECT
            MAX(salary) AS salary
        FROM
            employee );
-----------
-- 2st highest salary
-----------
SELECT
    e1.*
FROM
    employee AS e1
WHERE
    1 =
    (   SELECT
            COUNT(DISTINCT e2.salary)
        FROM
            employee AS e2
        WHERE
            e2.salary > e1.salary );
-----------
-- Nth highest salary
-----------
SELECT
    MAX(e1.salary)
FROM
    employee e1
WHERE
    salary NOT IN
    (   SELECT
            DISTINCT e2.salary
        FROM
            employee e2
        ORDER BY
            e2.salary DESC
        LIMIT
            1 )
GROUP BY
    e1.salary
ORDER BY
    salary DESC;
----------
----------
SELECT
    MAX( e1.salary)
FROM
    employee e1;
-------------
-- department top three salaries
------------
SELECT
    d.name  AS department,
    e1.name AS employee,
    e1.salary
FROM
    employee AS e1
JOIN
    department AS d
ON
    d.id = e1.departmentid
WHERE
    3 >
    (   SELECT
            COUNT(DISTINCT e2.salary)
        FROM
            employee AS e2
        WHERE
            e2.salary > e1.salary
        AND e1.DepartmentId = e2.DepartmentId)
ORDER BY
    d.name, 
    e1.salary DESC;
--------------
--------------
SELECT
    d.name AS Department,
    e.name AS Employee,
    e.salary
FROM
    Employee AS e
JOIN
    Department AS d
ON
    e.departmentId = d.id
WHERE
    (
        e.departmentId,
        e.salary)
    IN
    (   SELECT
            DISTINCT e.departmentId,
            e.salary
        FROM
            Employee AS e
        ORDER BY
            e.salary DESC
        LIMIT
            3 );
--------------
--------------
SELECT
    d.Name  AS Department,
    e1.Name AS Employee,
    e1.Salary
FROM
    Employee e1
JOIN
    Department d
ON
    e1.DepartmentId = d.Id
WHERE
    3 >
    (   SELECT
            COUNT(DISTINCT e2.Salary)
        FROM
            Employee e2
        WHERE
            e2.Salary > e1.Salary
        AND e1.DepartmentId = e2.DepartmentId );
            