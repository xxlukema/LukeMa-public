SET serveroutput ON format wrapped;
DECLARE
  p_utility_type      VARCHAR2(20);
  p_facility_id       VARCHAR2(20);
  p_area_code         VARCHAR2(20);
  p_district_code     VARCHAR2(20);
  p_start_year        NUMBER;
  p_end_year          NUMBER;
  p_start_month       NUMBER;
  p_end_month         NUMBER ;
  p_normalized        VARCHAR2(20);
  p_is_like_buildings VARCHAR2(20);
  p_complete_dataset  VARCHAR2(20);
  p_cursor UWS_UTILITY_INVOICE_PKG.monthly_util_cursor;
  p_show_area     CHAR;
  p_show_district CHAR;
  p_show_facility CHAR;
  p_show_utility  CHAR;
  --
  area_code uws_monthly_util_cache_t.area_code%type;
  area_name uws_monthly_util_cache_t.area_name%type;
  district_code uws_monthly_util_cache_t.district_code%type;
  district_name uws_monthly_util_cache_t.district_name%type;
  facility_id uws_monthly_util_cache_t.facility_id%type;
  facility_name uws_monthly_util_cache_t.facility_name%type;
  service_type uws_monthly_util_cache_t.service_type%type;
  fiscal_year uws_monthly_util_cache_t.fiscal_year%type;
  fiscal_month uws_monthly_util_cache_t.fiscal_month%type;
  sum_interior_sf uws_monthly_util_cache_t.sum_interior_sf%type;
  fac_count uws_monthly_util_cache_t.fac_count%type;
  sum_mon_kbtu_units uws_monthly_util_cache_t.sum_mon_kbtu_units%type;
  sply_kbtu_consump uws_monthly_util_cache_t.sply_kbtu_consump%type;
  sum_mon_cost uws_monthly_util_cache_t.sum_mon_cost%type;
  sply_cost uws_monthly_util_cache_t.sply_cost%type;
BEGIN
  p_utility_type      := 'ELE';
  p_facility_id       := '522003G01';
  p_area_code         := NULL;
  p_district_code     := NULL;
  p_start_year        := 2013;
  p_end_year          := 2018;
  p_start_month       := 10;
  p_end_month         := 9;
  p_normalized        := 'N';
  p_is_like_buildings := NULL;
  p_complete_dataset  := 'N';
  --p_cursor uws.UWS_UTILITY_INVOICE_PKG.monthly_util_cursor;
  p_show_area     := NULL;
  p_show_district := NULL;
  p_show_facility := NULL;
  p_show_utility  := NULL;
  --
  DBMS_OUTPUT.put_line('msg: ' || 'a');
  --
  --UWS_UTILITY_INVOICE_PKG.get_monthly_by_util( p_utility_type, p_facility_id, p_area_code, p_district_code, p_start_year, p_end_year, p_start_month , p_end_month , p_normalized , p_is_like_buildings , p_complete_dataset , p_cursor , p_show_area , p_show_district , p_show_facility , p_show_utility );
  UWS_UTILITY_INVOICE_PKG.get_monthly_by_util( p_utility_type, p_facility_id, p_area_code, p_district_code, p_start_year, p_end_year, p_start_month , p_end_month , p_normalized , p_is_like_buildings , p_complete_dataset , p_cursor);
  --
  DBMS_OUTPUT.put_line('msg: ' || 'completed.');
  --
  LOOP
    FETCH p_cursor
    INTO area_code ,
      area_name ,
      district_code ,
      district_name ,
      facility_id ,
      facility_name ,
      service_type ,
      fiscal_year ,
      fiscal_month ,
      sum_interior_sf ,
      fac_count ,
      sum_mon_kbtu_units ,
      sply_kbtu_consump ,
      sum_mon_cost ,
      sply_cost;
    EXIT
  WHEN p_cursor%NOTFOUND;
    dbms_output.put_line(facility_name || ' ' || fiscal_year || ' ' || sum_mon_kbtu_units);
  END LOOP;
  CLOSE p_cursor;
END;