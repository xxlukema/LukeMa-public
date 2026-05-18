CREATE TABLE
    ext_table_sale
    (
        id NUMBER,
        DESC VARCHAR2(20)
    )
    organization external
    (
        type oracle_loader 
        DEFAULT directory ext_dir 
        access parameters (
           records delimited BY "</token_row>" 
           badfile ext_dir:'sale.bad' 
           logfile ext_dir:'sale.log' 
           fields ( 
              delim CHAR (2000 ) 
              terminated BY "<token_row>", 
              token CHAR(256) enclosed BY "<token>" AND "</token>" 
           )
        ) 
        location ('test.xml')
    )
    reject limit unlimited nomonitoring