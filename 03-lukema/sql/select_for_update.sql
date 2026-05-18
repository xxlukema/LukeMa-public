select REQUEST_ID, REQUEST_TYPE from REQUEST where REQUEST_ID = 20026 for update of REQUEST_TYPE;

update request set REQUEST_TYPE = 'UserAccountRequest' where REQUEST_ID = 20026;

update request set REMARKS = 'test UserAccountRequest' where REQUEST_ID = 20026;

commit;

/
