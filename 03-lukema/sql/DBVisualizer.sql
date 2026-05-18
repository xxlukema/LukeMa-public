--select * from request where request_id = 17369;

--select * from COMPANY where COMPANY_NAME = 'LMa6-REQUEST-59040545009046897';

--select * from CUSTOMER_BUSINESS_UNIT where COMPANY_ID = 15013;

--select * from PROFILE_ASSIGNMENT_REQUEST where PROFILE_ASSIGNMENT_REQUEST_ID = 10569;

--select * from COMPANY where COMPANY_ID = 15032;

--select distinct REQUEST_TYPE from request;

--select * from request where REQUEST_TYPE = 'UserAccountRequest' order by reference_id ;

--select * from ACCOUNTREQUEST where CBU_ID is not null;

select ar.* from ACCOUNTREQUEST ar, request r where ar.CBU_ID = 10003 and ar.ACCOUNTREQUEST_ID = r.REFERENCE_ID;

