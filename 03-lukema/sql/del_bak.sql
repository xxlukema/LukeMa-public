delete from cat_assoc
where ref = '02CP2'
and   ltrim(rtrim(assoc_ref)) in ('9682', '9683')
/
