
--	<property name="hibernate.connection.url" value="jdbc:postgresql://database-bc-qa.bc.int:5432/att_transactions" />
--	<property name="hibernate.connection.username" value="att_transactions" />
--	<property name="hibernate.connection.password" value="venus99!" />


delete from "public"."att_sub_task";
delete from "public"."att_file_transfer_record";

commit;
