
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
<!-- mapping to a larger blob type.                                          -->
<!--                                                                         -->
      <property name="stringList" type="serializable" length="257" />

<!--                                                                         -->
<!-- If nap a Date to type="date", the millisecond data will be lost. To     -->
<!-- keep the millisecond field, map Date to type="java.util.Date"           -->
<!--                                                                         -->
      <property name="date" type="java.util.Date" />
