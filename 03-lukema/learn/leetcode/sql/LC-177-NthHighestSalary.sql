# LIMIT & OFFSET is not SQL standard.

select top 1 salary 
from employee
where salary not in (
    select top 7 salary
    from employee
    order by salary desc
)
order by salary desc;

select salary
from employee
order by salary desc
limit N offset 1;


CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
     N = N - 1;
  RETURN (
      # Write your MySQL query statement below.
      RETURN SELECT distinct salary 
             FROM employee
             order by salary desc
             LIMIT 1 OFFSET N;
      
  );
END


SELECT * FROM artists LIMIT [Number to Limit By];


SELECT * FROM artists LIMIT 5 OFFSET [Number of rows to skip];

# Say you want to get 5 artists, but not the first five. You want to get rows 3 through 8. You will want to add an OFFSET of 2 to skip the first two rows:

SELECT * FROM artists LIMIT 5 OFFSET 2;


Here is a challenge for you. Write a query to fetch the Artists in rows 10 through 20:

select * from employee limit 11 skip 9

Hint: You want to skip the first 9 and then only return 11 (10 through 20) results.


-- https://www.programmerinterview.com/database-sql/find-nth-highest-salary-sql/
-- Top highest salary
SELECT
    *
FROM 
    employee AS e1
WHERE
    0 = 
    (   SELECT 
            COUNT(DISTINCT 0)
        FROM 
            employee AS e2
        WHERE 
            e2.salary > e1.salary );

-- Third highest salary
SELECT
    *
FROM 
    employee AS e1
WHERE
    2 = 
    (   SELECT 
            COUNT(DISTINCT 0)
        FROM 
            employee AS e2
        WHERE 
            e2.salary > e1.salary );
            