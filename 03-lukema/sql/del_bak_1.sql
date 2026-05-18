delete from cat_assoc
where ref = '16CP6'
and 
(
   (ltrim(rtrim(assoc_ref)) != '2671' 
    and se_group = '00')
 or
   (ltrim(rtrim(assoc_ref)) != '16WI0' 
    and se_group = '03')
 or
   (ltrim(rtrim(assoc_ref)) != '16W03' 
    and se_group = '04')
 or
   (ltrim(rtrim(assoc_ref)) != '16XXY' 
    and se_group = '09')
)
/
