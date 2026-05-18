select REF, ASSOC_REF, 
       SE_GROUP, FREEDOM
from cat_assoc
where ref = '16PR2'
and   freedom != '00'
order by se_group, freedom
/
