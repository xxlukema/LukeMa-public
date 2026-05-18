SELECT MAX(SALARY) as SecondHighestSalary
FROM Employee
WHERE Salary NOT IN (
  SELECT MAX(Salary)
  FROM Employee
);

SELECT
    (SELECT DISTINCT
            Salary
        FROM
            Employee
        ORDER BY Salary DESC
        LIMIT 1 OFFSET 1) AS SecondHighestSalary
;

select top 1 salary from
(select distinct salary from Employee order by salary desc)
where salary not in
(select top 1 salary from
(select distinct salary from Employee order by salary desc)
)
