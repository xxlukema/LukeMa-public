
delete from ACCOUNTREQUEST where LAST_NAME like 'LMa6-REQUEST-%';
commit;

delete from PROFILE_ASSIGNMENT_REQUEST where WORKFLOW_NAME like 'LMa6-REQUEST-%';
commit;

delete from REQUEST_AUDITTRAIL where CHANGED_BY like 'LMa6-REQUEST-%';
commit;

delete from REQUEST where REQUESTOR like 'LMa6-REQUEST-%' ;
commit;



