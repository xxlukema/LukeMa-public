

Parent(1)-----------(*)ChildOne(1)-----------(1)ChildOneChild
   (1)
    |---------------(1)ChildTwo


Table size:

Parent: 30K rows
ChildOne: 40K rows
ChildTwo: 10K rows
ChildOneChild: 10K rows

Performance:

Insert: 
Auto: 20.000s
Manual: 18.437s

Retrieve All:
Auto: 16.202s
Manual: 643.354s

Conclusions:

1. Insertion: Manual is slightly faster than auto.
2. Retrieval: Auto is 40 times faster than Manual.
3. Named Queries: Manual needs named queries to build relationship. Auto dones not need named queries.
4. Code: Manual code is larger.
5. The about tests are on MySQL.


Input File: pom.xml

Table Size: 5,000 rows

CLOB:
Insertion: 4.859s
Retrieval: 4.813s

Text:
Insertion: 9.969s
Retrieval: 13.515s

Table Size: 10,000 rows

CLOB:
Insertion: 10.234s
Retrieval: 11.797s

Text:
Insertion: 18.093s
Retrieval: OutOfMemoryError


Conclusions:

1. Insertion: CLOB is 2 times faster than Text.
2. Retrieval: CLOB is 3 times faster than Text.
3. For "Retrieve All", Text version will get OutOfMemoryError when table has more data (5,000 rows). CLOB version has no problems even table size is 10,000 rows. CLOB version will get OutOfMemoryError when table size is more than 10,000 rows.
4. Input File: pom.xml
5. DB: MySQL.








