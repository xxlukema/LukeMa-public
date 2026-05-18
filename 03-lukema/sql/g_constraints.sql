select insert_date, FACILITY_ID, ACTIVITY_ID, START_DATE, TOP_MD, EXISTENCE_KIND, VERSION from GENERIC_ACT_FAC_INVL where insert_date >= sysdate - 1
/
