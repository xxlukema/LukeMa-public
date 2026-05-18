delete from oa_cat_assoc
where ref = '35CP8'
and 
(
   (ltrim(rtrim(assoc_ref)) != 'CONT1' 
    and se_group = '01')
 or
   (ltrim(rtrim(assoc_ref)) != '35WBX' 
    and se_group = '02')
 or
   (ltrim(rtrim(assoc_ref)) != 'DCN2' 
    and se_group = '03')
 or
   (ltrim(rtrim(assoc_ref)) != 'F000' 
    and se_group = '04')
 or
   (ltrim(rtrim(assoc_ref)) != '35RAP' 
    and se_group = '05')
 or
   (ltrim(rtrim(assoc_ref)) != '35PRO' 
    and se_group = '06')
 or
   (ltrim(rtrim(assoc_ref)) != '35WI0' 
    and se_group = '07')
 or
   (ltrim(rtrim(assoc_ref)) != '35CLD' 
    and se_group = '08')
 or
   (ltrim(rtrim(assoc_ref)) != '3511' 
    and se_group = '09')
)
/
