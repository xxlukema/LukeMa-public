CREATE TABLE
    ext_table_emp
    (
        id NUMBER,
        fname VARCHAR2(20),
        lname VARCHAR2(20)
    )
    organization external
    (
        type oracle_loader 
        DEFAULT directory ext_dir 
        access parameters ( 
           records delimited BY newline
           badfile ext_dir_err:'emp.bad'
           logfile ext_dir_err:'emp.log'
           discardfile ext_dir_err:'emp.dis'
           fields terminated BY '|' 
           missing field VALUES are NULL 
           (id, fname, lname)
        ) 
        location ('ext_table_emp.txt')
    )
    reject limit unlimited;