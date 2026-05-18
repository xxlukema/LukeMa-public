
CREATE TABLE
    aggregate_table
    (
        id BIGINT NOT NULL,
        fname varchar(20) NOT NULL,
        lname varchar(20) NOT NULL,
        salary DOUBLE PRECISION,
        height real,
        PRIMARY KEY (id)
    );

insert into aggregate_table (id, fname, lname, salary, height) values (1, 'Luke', 'Ma', 2000, 5.8);
insert into aggregate_table (id, fname, lname, salary, height) values (2, 'Luk1', 'Ma', 2100, 5.9);
insert into aggregate_table (id, fname, lname, salary, height) values (3, 'Luk2', 'Ma', 2200, 5.7);
insert into aggregate_table (id, fname, lname, salary, height) values (4, 'Luk3', 'Ma', 2300, 5.7);
insert into aggregate_table (id, fname, lname, salary, height) values (5, 'Hong', 'Lin', 1500, 4.3);
insert into aggregate_table (id, fname, lname, salary, height) values (6, 'Hon1', 'Lin', 1000, 4.2);
insert into aggregate_table (id, fname, lname, salary, height) values (7, 'Hon2', 'Lin', 800, 4.1);   

 