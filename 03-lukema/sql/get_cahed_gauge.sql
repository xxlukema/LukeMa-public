select unique g.SITE_BOREHOLE_ID from  recorder r, GENERIC_ACT_FAC_INVL g where r.id = g.FACILITY_ID and end_date is null
/
