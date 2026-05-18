# LC-184-Department Highest Salary
# Medium

# Runtime: 1015 ms, faster than 34.14% of MySQL online submissions for Department Highest Salary.
# Memory Usage: 0B, less than 100.00% of MySQL online submissions for Department Highest Salary.

select d.name as 'Department', e.name as 'Employee', e.salary
from Employee as e 
join Department as d 
on e.departmentId = d.id 
where e.salary = max(e.salary)


select departmentId, max(salary)
from Department
group by departmentId



select d.name as 'Department', e.name as 'Employee', e.salary 
from Employee as e 
join Department as d 
on e.departmentId = d.id 
where (e.departmentId, salary) in 
(
  select e.departmentId, max(e.salary) as salary 
  from Employee as e 
  group by departmentId
);




