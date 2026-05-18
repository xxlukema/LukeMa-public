select i.mnemonic, a.market, a.ref, a.assoc_ref, a.se_group,
a.min_freedom, a.max_freedom, a.as_charge,
a.effective_date, a.expiration_date, a.nl,
sysdate
from oa_se_assoc a, oa_se_info i
where a.market='61'
and a.ref = i.ref
and a.market = i.market
              and mnemonic in ('***', 'GLF') 
and a.ref in (select ref 
              from oa_se_info 
              where ref = '61HP0' 
              and mnemonic in ('***', 'GLF') 
              and plan_set in ('00', '13'))
and ltrim(rtrim(a.assoc_ref)) in ('61NOC', 'F1YR', 'F2YR', 
                                  'F3YR', '6135O', '61CF', 
                                  '8267', 'FASCW', 'TC261', 
                                  '61IM4', '61RDA')
order by i.mnemonic, a.market, a.ref, a.assoc_ref
/
