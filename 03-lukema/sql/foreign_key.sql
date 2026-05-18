SELECT TABLE_NAME, COLUMN_NAME FROM User_cons_columns WHERE constraint_name in (SELECT constraint_name FROM user_constraints WHERE constraint_type = 'R' and table_name = '&table_name')
/
