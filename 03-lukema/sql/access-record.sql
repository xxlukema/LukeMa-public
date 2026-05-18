select * from access_records
where remoteaddress not like '65.124.76.%'
and remoteaddress != '127.0.0.1'
order by datecreated desc;