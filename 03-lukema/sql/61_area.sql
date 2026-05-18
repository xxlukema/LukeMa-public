select distinct r.ref, r.des, r.techrestriction, i.mnemonic, 
       i.effective_date, i.expiration_date, r.oasys_type
from oa_se_ref r, oa_se_info i, oa_se_assoc a 
where r.ref=i.ref 
  and a.ref = r.ref 
  and a.market = i.market 
  and a.market = r.market 
  and r.oasys_type='P' 
  and (r.oasys_subtype is null or r.oasys_subtype <> 's') 
  and r.market = '61' 
  and r.ref = 'WM261'
--  and i.mnemonic in ('***', '***') 
  and i.plan_set in ('00', '13', '90') 
  and (r.techrestriction is null or r.techrestriction = 'T')
/
