select market, ref, assoc_ref
from oa_cat_assoc
minus
(select market, ref, assoc_ref
 from oa_se_assoc)
order by market, ref, assoc_ref
/
