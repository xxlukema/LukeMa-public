select cat.min_freedom, cat.max_freedom, cat.se_group, 
       cat.ref ref, ref.ref assoc_ref, ref.des, 
       cat.commitment, i.mnemonic, 
       i.effective_date LifeEff, i.expiration_date LifeExp, 
       cat.effective_date AssocEff, cat.expiration_date AssocExp,
       i.plan_set,  i.mnemonic, i.expiration_date, i.effective_date 
from oa_se_ref ref, oa_cat_assoc cat, oa_se_info i 
where ref.ref = cat.assoc_ref 
  and ref.ref = i.ref 
--  and i.plan_set in ('00', '13') 
  and cat.ref in ('15CN6') 
--  and i.mnemonic in ('***', '***') 
--  and (i.expiration_date is null or to_date(i.expiration_date, 'YYYYMMDD') > to_date(20020813,'YYYYMMDD')) 
--  and (cat.expiration_date is null or to_date(cat.expiration_date, 'YYYYMMDD') > to_date(20020813,'YYYYMMDD')) 
--  and (i.effective_date is null or to_date(i.effective_date, 'YYYYMMDD') <= to_date(20020813,'YYYYMMDD')) 
--  and (cat.effective_date is null or to_date(cat.effective_date, 'YYYYMMDD') <= to_date(20020813,'YYYYMMDD')) 
order by cat.ref, cat.se_group, ref.ref
/
