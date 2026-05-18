# Easy
# Customers who never order
# Runtime: 773 ms, faster than 32.52% of MySQL online submissions for Customers Who Never Order.
# Memory Usage: 0B, less than 100.00% of MySQL online submissions for Customers Who Never Order.

select c.name as Customers
from Customers as c 
where c.id not in (
  select o.customerId as id
  from Orders as o
);