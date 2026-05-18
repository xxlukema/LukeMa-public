select position, COLUMN_NAME
from user_cons_columns
where CONSTRAINT_NAME = '&CONSTRAINT_NAME'
/
