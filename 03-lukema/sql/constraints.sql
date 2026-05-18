select CONSTRAINT_NAME, CONSTRAINT_TYPE
from user_constraints
where TABLE_NAME = '&TABLE_NAME'
/
