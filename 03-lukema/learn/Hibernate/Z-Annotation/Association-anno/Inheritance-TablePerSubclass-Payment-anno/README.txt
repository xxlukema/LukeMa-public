
Transactions and Atomic Operations

MySQL Server (version 3.23-max and all versions 4.0 and above) supports 
transactions with the InnoDB and BDB transactional storage engines. 
InnoDB provides full ACID compliance. See Chapter 13, Storage Engines. 
For information about InnoDB differences from standard SQL with regard to 
treatment of transaction errors, see Section 13.2.15, ¡°InnoDB Error 
Handling¡±.

The other non-transactional storage engines in MySQL Server (such as MyISAM) 
follow a different paradigm for data integrity called ¡°atomic operations.¡± 
In transactional terms, MyISAM tables effectively always operate in 
AUTOCOMMIT=1 mode. Atomic operations often offer comparable integrity with 
higher performance. 

It's understood that we have to create a table with engine type as 'InnoDB' 
for the transactions to be handled.


The template instances are thread-safe and reusable, 
they can thus be kept as instance variables of the surrounding class.


