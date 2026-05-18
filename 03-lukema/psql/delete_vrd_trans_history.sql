
--	<property name="hibernate.connection.url" value="jdbc:postgresql://database-bc-int2.bc.int:5432/vrdfile" />
--	<property name="hibernate.connection.username" value="vrdfile" />
--	<property name="hibernate.connection.password" value="vrdfile" />


delete from "public"."vz_sub_task";
delete from "public"."vz_file_transfer_record";

commit;
