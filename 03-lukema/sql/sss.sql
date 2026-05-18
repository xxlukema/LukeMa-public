select insert_date, to_char(start_date, 'yyyy/mm/dd hh:mi:ssam'), FACILITY_ID, SITE_BOREHOLE_ID, BASE_MD from GENERIC_ACT_FAC_INVL where insert_date > sysdate - 1 order by insert_date
/
