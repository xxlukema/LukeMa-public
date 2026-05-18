

1. Hibernate Version Issue

      <dependency>
         <groupId>org.hibernate</groupId>
         <artifactId>hibernate</artifactId>
         <version>3.1</version>
<!--
         <version>3.0.5</version>         ========= column id not found exception at runtime.
         <version>3.1</version>           ========= scalar sequence messed up with this version of hibernate.    
         <version>3.2.0.ga</version>      ========= scalar sequence get fixed with this version of hibernate.  
         <version>3.2.5.ga</version>
-->
      </dependency>


2. MySQL Persistence Issue

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

3. Mapping java.util.Date and java.util.List

The "serializable" type defaults to tinyBlob using the MySQLInnoDB 
dialect, which has size 255 bytes. Many serialized objects are larger, 
causing EOF exceptions. Use length="257" or some such to trigger 
mapping to a larger blob type. 

Example:

<property name="DataMap" column="data_map" type="serializable" length="257"/>


<!--                                                                         -->
<!-- The "serializable" type defaults to tinyBlob using the MySQLInnoDB      -->
<!-- dialect, which has size 255 bytes. Many serialized objects are larger,  -->
<!-- causing EOF exceptions. Use length="257" or some such to trigger        -->
<!-- mapping to a larger blob type for MySQL. For Oracle, use                -->
<!-- length = "2001" to trigger mapping to LONG RAW.                         -->
<!--                                                                         -->
      MySQL:  <property name="stringList" type="serializable" length="257" />
      Oracle: <property name="stringList" type="serializable" length="2001" />

<!--                                                                         -->
<!-- If nap a Date to type="date", the millisecond data will be lost. To     -->
<!-- keep the millisecond field, map Date to type="java.util.Date"           -->
<!--                                                                         -->
      <property name="date" type="java.util.Date" />


