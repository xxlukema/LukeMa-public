
/*
select 
table_name,
code,
sort_order,
status,
user_id,
code_up,
sys_company
from lookup_tables 
where sys_company = 102
and status = 1;
*/

/*
select 
*
from lookup_tables 
where code like 'fisc%'
and status = 1;
*/



select lhs.value, rhs.*
from preference lhs, preference rhs
where rhs.sys_company = 102
and lhs.sys_company = 0
and lhs.id = 0
and lhs.code = rhs.code
and rhs.pref_group = 'system'
order by lhs.code;


-- update lookup_tables set description = 'Chinese Yuan' from lookup_tables where code = 'CNY';

-- select * from sys_menu;

/*
select * from preference 
where id = 102
and code = 'showtutorial_yn';
*/

/*
delete from preference 
where id = 102 and preference_id = 1863;
*/

/*
SELECT OBJECT_NAME(OBJECT_ID) AS NameofConstraint,
SCHEMA_NAME(schema_id) AS SchemaName,
OBJECT_NAME(parent_object_id) AS TableName,
type_desc AS ConstraintType
FROM sys.objects
WHERE type_desc LIKE '%CONSTRAINT'
*/

/*
select distinct lhs.value 'label', rhs.value 'value', lhs.code, rhs.preference_id, rhs.id
from preference lhs, preference rhs
where rhs.sys_company = 102
and lhs.sys_company = 0
and lhs.id = 0
and lhs.code = rhs.code
and rhs.pref_group = 'system'
order by lhs.code;
*/

/*
select 
table_name,
code,
sort_order,
status,
user_id,
code_up,
sys_company
from lookup_tables
where sys_company = 102
order by 2, 3, 1;
*/

/*
select 
   *
from preference where sys_company = 0
and id = 0
and visible = 1
order by code;
*/

/*
select distinct
   prmry_group,
   scndry_group
from preference where sys_company = 0
and id = 0
and visible = 1
order by 1, 2;
*/


/*
select
   *
from preference where sys_company = 102
and pref_group = 'system'
order by code;
*/

/*
select 
   sys_company,
   module,
   pref_group,
   code,
   value
from preference where sys_company = 0
order by code;
*/


/*
select * from lookup_tables
where table_name = 'preference_group';
*/

/*
select distinct lhs.value 'label', rhs.value 'value', lhs.code, rhs.preference_id, rhs.id
from preference lhs, preference rhs
where rhs.sys_company = 102
and lhs.sys_company = 0
and lhs.id = 0
and lhs.code = rhs.code
and rhs.pref_group = 'user'
order by lhs.code;
*/

/*
select distinct lhs.value 'label', rhs.value 'value', lhs.code, rhs.preference_id, rhs.id, lhs.prmry_group, lhs.scndry_group
from preference lhs, preference rhs
where rhs.sys_company = 102
and lhs.sys_company = 0
and lhs.id = 0
and lhs.code = rhs.code
and rhs.pref_group = 'user'
order by lhs.code;
*/

/*
select * from preference
where sys_company = 102
and pref_group = 'user';
*/

/*
select * from preference
where id = 0
and sys_company = 0
and pref_group like '%ser' ;
*/


/*
update preference set pref_group = 'user'
where pref_group = 'User'
and id = 0
and sys_company = 0;
*/

/*
update preference set scndry_group = 'User'
where scndry_group = 'user'
and id = 0
and sys_company = 0;

update preference set prmry_group = 'User'
where prmry_group = 'user'
and id = 0
and sys_company = 0;
*/


/*
select distinct prmry_group, scndry_group
from preference
order by 1;
*/

/*
select * from preference
where id = 0 and sys_company = 0 and pref_group = 'system'
;
--and value = 'Company Name';
*/


/*
update preference set prmry_group = 'Company', scndry_group = 'Company Information'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Company Name' 
or value = 'Logo File Name' 
or value = 'Web Site' 
or value = 'Sys Admin User Id' 
);

update preference set prmry_group = 'Company', scndry_group = 'Country'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Company Language' 
or value = 'Default Country' 
);

update preference set prmry_group = 'Company', scndry_group = 'Financial'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Home Currencye' 
or value = 'Fiscal Year Begin Month' 
);

update preference set prmry_group = 'Application', scndry_group = 'File System and Report'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Directory Structure' 
or value = 'Virtual directory for files' 
or value = 'Crystal reporting server' 
);

update preference set value = 'Path to Application Files'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Directory Structure' ;

update preference set value = 'Virtual File Directory'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Virtual directory for files' ;

update preference set value = 'Crystal Reporting Server'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Crystal reporting server' ;

update preference set prmry_group = 'Application', scndry_group = 'Search'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Max number of results to display in certain lookups' 

update preference set value = 'Max # of Search Results'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Max number of results to display in certain lookups' ;

update preference set prmry_group = 'Application Email', scndry_group = 'Mail Server'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Mail host' 
or value = 'Mail server user id' 
or value = 'Mail password' 
or value = 'Domain for E-Mail' 
or value = 'Email Link Server' 
);

update preference set value = 'Mail Server Domain Name'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Domain for E-Mail' ;

update preference set value = 'Application Email URL'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Email Link Server' ;

update preference set prmry_group = 'Application Email', scndry_group = 'Mail'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Send mail for new action items' 
or value = 'Send mail for new action items' 
or value = 'Send mail for new action items' 
or value = 'Use Logon for E-Mail' 
or value = 'Length of logon ID used for E-Mail address' 
);

update preference set value = 'Logon ID Length'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Length of logon ID used for E-Mail address' ;

update preference set value = 'Use Logon ID for Email Address'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Use Logon for E-Mail' ;

update preference set value = 'Send Email for New Action Items'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Send mail for new action items' ;

update preference set prmry_group = 'Authentication', scndry_group = 'Application Authentication'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Force password change during first time login' 
or value = 'Force an alphanumeric password to be entered' 
or value = 'Minimum length of password' 
or value = 'Days Between user password expiration' 
or value = 'No of old passwords to keep' 
or value = 'Enable email pwd on Failed Login' 
);

update preference set value = 'Force Alphanumeric Passwords'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Force an alphanumeric password to be entered' ;

update preference set value = 'Days Before Password Expires'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Days Between user password expiration' ;

update preference set value = 'Number of Old Passwords to Keep'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'No of old passwords to keep' ;

update preference set value = 'Force Password Change on First Login'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Force password change during first time login' ;

update preference set prmry_group = 'Authentication', scndry_group = 'External Authentication'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Ext Authentication Engine' 
or value = 'Ext Authentication Engine Type' 
or value = 'Ext Authentication Exempt Users' 
);

update preference set value = 'External Authentication Engine'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Ext Authentication Engine' ;

update preference set value = 'External Authentication Engine Type'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Ext Authentication Engine Type' ;

update preference set value = 'External Authentication Exempt Users'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Ext Authentication Exempt Users' ;

update preference set prmry_group = 'Help', scndry_group = 'Help System'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Show Help Link' 
or value = 'URL of help file' 
or value = 'Show Tutorial Link' 
or value = 'Tutorial URL' 
or value = 'Help Source' 
);

update preference set value = 'Help File URL'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'URL of help file' ;

update preference set value = 'Help Contact Name'
where id = 0 and sys_company = 0 and pref_group = 'system'
and value = 'Help Source' ;

update preference set prmry_group = 'User', scndry_group = 'User'
where id = 0 and sys_company = 0 and pref_group = 'user'
and ( 
value = 'Allow user to change manager' 
or value = 'Allow users to update user info' 
or value = 'Enable blind SSN entry' 
);

update preference set prmry_group = 'User', scndry_group = 'Surrogate'
where id = 0 and sys_company = 0 and pref_group = 'user'
and ( 
value = 'Allow multiple surrogates for a manager' 
or value = 'Limit surrogate to this many days' 
);

update preference set prmry_group = 'company', scndry_group = 'financial'
where id = 0 and sys_company = 0 and pref_group = 'system'
and ( 
value = 'Home Currency' 
);



*/



-- select * from preference where id = 0 and sys_company = 0 and pref_group = 'user' order by value;


-- select * from preference where id = 0 and sys_company = 0 and pref_group = 'system' order by value;

/*
select * from lookup_tables 
where table_name = 'preference_group'
and status = 1;
*/


/*
update  lookup_tables 
set description = 'System Preference'
where table_name = 'preference_group'
and code = 'system'
and status = 1;
*/

/*
update  lookup_tables 
set description = 'User Preference'
where table_name = 'preference_group'
and code = 'user'
and status = 1;
*/







