# LC-185-Department Top Three Salaries
# Hard

# https://www.programmerinterview.com/database-sql/find-nth-highest-salary-sql/

select d.name as 'Deppartment', e.name as 'Employee', e.salary
from Employee as e 
join Department as d 
on e.departmentId = d.id 
where (e.departmentId, e.salary)
in (
   select distinct e.departmentId, e.salary
   from Employee as e
   order by e.salary desc
   limit 3
);

# Runtime: 1238 ms, faster than 40.43% of MySQL online submissions for Department Top Three Salaries.
# Memory Usage: 0B, less than 100.00% of MySQL online submissions for Department Top Three Salaries.

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