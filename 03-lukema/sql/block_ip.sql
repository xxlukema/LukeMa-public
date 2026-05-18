
insert into access_blocked_list
(id, remoteAddress, dateCreated, symbol)
values
(AccessBlocked_Id_Seq.nextval, '127.0.0.1', sysdate, 'hov');

commit;

