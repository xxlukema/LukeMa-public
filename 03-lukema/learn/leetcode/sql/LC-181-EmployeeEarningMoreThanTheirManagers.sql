select e.id, e.name, e.salary, m.id, m.name, m.salary
from employee e
join employee m
on 
   e.managerId = m.id 
where e.salary > m.salary





