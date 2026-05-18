delete from cat_assoc
where ref = '16CN8'
and 
(
   (ltrim(rtrim(assoc_ref)) != '2671' 
    and se_group = '00')
 or
   (ltrim(rtrim(assoc_ref)) != '2786' 
    and se_group = '03')
 or
   (ltrim(rtrim(assoc_ref)) != '9716' 
    and se_group = '04')
 or
   (ltrim(rtrim(assoc_ref)) != 'NONE1' 
    and se_group = '06')
 or
   (ltrim(rtrim(assoc_ref)) != '16XXY' 
    and se_group = '09')
)
/
