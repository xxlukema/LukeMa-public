
If you use a List (or other indexed collection) you need to set the key 
column of the foreign key to not null, and let Hibernate manage the 
association from the collections side to maintain the index of each 
element (making the other side virtually inverse by setting 
update="false" and insert="false").


