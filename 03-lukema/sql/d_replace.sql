delete from cat_assoc
where ref = '16PR6'
and 
(
   (ltrim(rtrim(assoc_ref)) != '2672' 
    and se_group = '00')
 or
   (ltrim(rtrim(assoc_ref)) != '16CUN' 
    and se_group = '03')
 or
   (ltrim(rtrim(assoc_ref)) != '4281' 
    and se_group = '04')
 or
   (ltrim(rtrim(assoc_ref)) != '16XXY' 
    and se_group = '09')
)
/
