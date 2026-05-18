CREATE OR REPLACE PACKAGE BODY "UWS"."UWS_UTILITY_INVOICE_PKG" is

---ADD the PROCEDURE below to DEFMS for testing
--------------------------------------------------------------------------------
/* Procedure for the table in the eems dashboard when facilities selected*/
procedure get_table_summary_facilities(p_start_year            number,
                               p_end_year              number,
                               p_start_month           number,
                               p_end_month             number,
                               p_is_like_buildings     varchar2,
                               p_normalized            varchar2,
                               p_complete_dataset      varchar2,
                               p_facility_id           varchar2 default null,
                               p_area_code             varchar2 default null,
                               p_district_code         varchar2 default null,
                              p_cursor            out table_summary_cursor) as
    v_start_fy           number := p_start_year + floor(p_start_month / 10); -- Oct, Nov, Dec
    v_end_fy             number := p_end_year + floor(p_end_month / 10);     -- Oct, Nov, Dec
    v_start_fy_month     number := mod(p_start_month + 2, 12) + 1;
    v_end_fy_month       number := mod(p_end_month + 2, 12) + 1;
    v_start_yyyymm       number := v_start_fy || lpad(v_start_fy_month, 2, 0);
    v_end_yyyymm         number := v_end_fy || lpad(v_end_fy_month, 2, 0);
    v_prev_start_yyyymm  number := (v_start_fy - 1) || lpad(v_start_fy_month, 2, 0);
    v_prev_end_yyyymm    number := (v_end_fy - 1) || lpad(v_start_fy_month, 2, 0);

    v_show_district      char(1) := case when coalesce(p_area_code, p_district_code, p_facility_id) is null then 'N' else 'Y' end;
    v_show_facility      char(1) := case when nvl(p_facility_id,p_district_code) is null then 'N' else 'Y' end;

    v_area_codes         varchar2(4000) := replace(p_area_code,',','|');
    v_district_codes     varchar2(4000) := replace(p_district_code,',','|');
    v_facility_ids       varchar2(4000) := replace(p_facility_id,',','|');
BEGIN
      OPEN p_cursor FOR
                select   area_code, area_name, district_code, district_name,
               facility_id, facility_name, sum_interior_sf, fac_count,
               sum_mon_kbtu_units, sply_kbtu_consump, sum_mon_cost, sply_cost, prev_kbtu, prev_cost
          FROM(select area_code, area_name,district_code,district_name,facility_id,facility_name, sum_interior_sf,
           fac_count, kbtu  sum_mon_kbtu_units,
             --decode(kbtu, 0, 0, round((1 - kbtu / prev_kbtu) * -100, 2)) sply_kbtu_consump,
           nvl(round((1 - nullif(kbtu,0) / nullif(prev_kbtu,0)) * -100, 2),0) sply_kbtu_consump,
           cost  sum_mon_cost,--decode(cost, 0, 0, round((1 - cost / prev_cost ) * -100, 2)) sply_cost,
           nvl(round((1 - nullif(cost,0) / nullif(prev_cost,0)) * -100, 2),0) sply_cost,
                   prev_kbtu,
                   prev_cost
                  from   (select   area_code,area_name,district_code,district_name,
           facility_id,facility_name,sum(decode('Y','Y',interior_sf))   sum_interior_sf,
           count(decode('Y','Y',1)) fac_count,sum(sum_mon_kbtu_units) kbtu,
           sum(sum_mon_cost) cost,
           sum(sum_prev_year_mon_kbtu_units) prev_kbtu,
           sum(sum_prev_year_mon_cost) prev_cost
          from (select   area_code,area_name,
          decode(v_show_district,'Y',district_code) district_code,
          decode(v_show_district,'Y',district_name) district_name,
          decode(v_show_facility,'Y',facility_id) facility_id,
          decode(v_show_facility,'Y',facility_name) facility_name,
                  interior_sf,
          max(has_cc_data) has_cc_data,
                  nvl(sum(decode(p_normalized,'Y',mon_norm_kbtu_units,mon_kbtu_units)),0) sum_mon_kbtu_units,
                  sum(mon_cost) sum_mon_cost,
                  nvl(sum(decode(p_normalized,'Y',prev_year_mon_norm_kbtu_units,prev_year_mon_kbtu_units)),0) sum_prev_year_mon_kbtu_units,
                  sum(prev_year_mon_cost) sum_prev_year_mon_cost
from     uws.uws_utility_monthly_fac_mv
where    fiscal_year_month between v_start_yyyymm  and v_end_yyyymm
and      is_like_building = nvl(p_is_like_buildings,is_like_building)
         and      1 = case
                 when p_complete_dataset = 'N' then 1
                      when p_complete_dataset = 'Y' and
                           p_normalized = 'N' and
                           v_start_yyyymm      >= curr_year_start_yyyymm and
                           v_end_yyyymm        <= curr_year_end_yyyymm and
                           v_prev_start_yyyymm >= prev_year_start_yyyymm and
                           v_prev_end_yyyymm   <= prev_year_end_yyyymm
                       then 1
                       when p_complete_dataset = 'Y' and
                           p_normalized = 'Y' and
                           v_start_yyyymm      >= curr_year_norm_start_yyyymm and
                           v_end_yyyymm        <= curr_year_norm_end_yyyymm and
                           v_prev_start_yyyymm >= prev_year_norm_start_yyyymm and
                           v_prev_end_yyyymm   <= prev_year_norm_end_yyyymm
                       then 1
                       else 0
                 end
--and      facility_id in (p_facility_id)
--and      (v_facility_ids is null or regexp_count(facility_id, v_facility_ids) > 0)
--and      facility_id in (select est_ebt_building_id from eems.eems_station_t where est_ebt_building_id is not null)
and      facility_id in (select est_ebt_building_id from fms.eems_station_v where est_ebt_building_id is not null)
              group by area_code, area_name, district_code, district_name, facility_id, facility_name, interior_sf)
              group by area_code, area_name, district_code, district_name, facility_id, facility_name)) ;
END;

--------------------------------------------------------------------------------
  /* Procedure for the table in the eems dashboard*/

  procedure get_table_summary (p_start_year            number,
                               p_end_year              number,
                               p_start_month           number,
                               p_end_month             number,
                               p_is_like_buildings     varchar2,
                               p_normalized            varchar2,
                               p_complete_dataset      varchar2,
                               p_facility_id           varchar2 default null,
                               p_area_code             varchar2 default null,
                               p_district_code         varchar2 default null,
                               p_station_type          varchar2 default null,
                               p_cursor            out table_summary_cursor) as
    v_start_fy           number := p_start_year + floor(p_start_month / 10); -- Oct, Nov, Dec
    v_end_fy             number := p_end_year + floor(p_end_month / 10);     -- Oct, Nov, Dec
    v_start_fy_month     number := mod(p_start_month + 2, 12) + 1;
    v_end_fy_month       number := mod(p_end_month + 2, 12) + 1;
    v_start_yyyymm       number := v_start_fy || lpad(v_start_fy_month, 2, 0);
    v_end_yyyymm         number := v_end_fy || lpad(v_end_fy_month, 2, 0);
    v_prev_start_yyyymm  number := (v_start_fy - 1) || lpad(v_start_fy_month, 2, 0);
    v_prev_end_yyyymm    number := (v_end_fy - 1) || lpad(v_start_fy_month, 2, 0);

    v_show_district      char(1) := case when coalesce(p_area_code, p_district_code, p_facility_id) is null then 'N' else 'Y' end;
    v_show_facility      char(1) := case when nvl(p_facility_id,p_district_code) is null then 'N' else 'Y' end;

    v_area_codes         varchar2(4000) := replace(p_area_code,',','|');
    v_district_codes     varchar2(4000) := replace(p_district_code,',','|');
    v_facility_ids       varchar2(4000) := replace(p_facility_id,',','|');
  begin
    update uws_table_summary_cache_t
    set    use_date = sysdate
    where  start_year_in         = p_start_year
    and    end_year_in           = p_end_year
    and    start_month_in        = p_start_month
    and    end_month_in          = p_end_month
    and    (is_like_buildings_in = p_is_like_buildings
            or nvl(is_like_buildings_in,p_is_like_buildings) is null)
    and    normalized_in         = p_normalized
    and    complete_dataset_in   = p_complete_dataset
    and    (facility_id_in       = p_facility_id
            or nvl(facility_id_in,p_facility_id) is null)
    and    (area_code_in         = p_area_code
            or nvl(area_code_in,p_area_code) is null)
    and    (district_code_in     = p_district_code
            or nvl(district_code_in,p_district_code) is null)
    and    (station_type_in     = p_station_type
            or nvl(station_type_in,p_station_type) is null);

    if sql%rowcount = 0 then
      insert into uws_table_summary_cache_t
      select sysdate, sysdate, p_start_year, p_end_year, p_start_month, p_end_month,
             p_is_like_buildings, p_normalized, p_complete_dataset,
             p_facility_id, p_area_code, p_district_code,
             area_code,
             area_name,
             district_code,
             district_name,
             facility_id,
             facility_name,
             sum_interior_sf,
             fac_count,
             kbtu  sum_mon_kbtu_units,
             --decode(kbtu, 0, 0, round((1 - kbtu / prev_kbtu) * -100, 2)) sply_kbtu_consump,
             nvl(round((1 - nullif(kbtu,0) / nullif(prev_kbtu,0)) * -100, 2),0) sply_kbtu_consump,
             cost  sum_mon_cost,
             --decode(cost, 0, 0, round((1 - cost / prev_cost ) * -100, 2)) sply_cost,
             nvl(round((1 - nullif(cost,0) / nullif(prev_cost,0)) * -100, 2),0) sply_cost,
            prev_kbtu,
            prev_cost,
            p_station_type,
            station_type
      from   (select   area_code,
                       area_name,
                       district_code,
                       district_name,
                       facility_id,
                       facility_name,
                       sum(decode(has_cc_data,'Y',interior_sf))   sum_interior_sf,
                       count(decode(has_cc_data,'Y',1))           fac_count,
                       sum(sum_mon_kbtu_units)                    kbtu,
                       sum(sum_mon_cost)                          cost,
                       sum(sum_prev_year_mon_kbtu_units)          prev_kbtu,
                       sum(sum_prev_year_mon_cost)                prev_cost,
                       station_type
              from     (select   area_code,
                                 area_name,
                                 decode(v_show_district,'Y',district_code) district_code,
                                 decode(v_show_district,'Y',district_name) district_name,
                                 decode(v_show_facility,'Y',facility_id) facility_id,
                                 decode(v_show_facility,'Y',facility_name) facility_name,
                                 interior_sf,
                                 max(has_cc_data) has_cc_data,
                                 nvl(sum(decode(p_normalized,'Y',mon_norm_kbtu_units,mon_kbtu_units)),0) sum_mon_kbtu_units,
                                 sum(mon_cost) sum_mon_cost,
                                 nvl(sum(decode(p_normalized,'Y',prev_year_mon_norm_kbtu_units,prev_year_mon_kbtu_units)),0) sum_prev_year_mon_kbtu_units,
                                 sum(prev_year_mon_cost) sum_prev_year_mon_cost,
                                 station_type
                        from     uws_utility_monthly_fac_mv
                        where    fiscal_year_month between v_start_yyyymm and v_end_yyyymm
                        and      is_like_building = nvl(p_is_like_buildings,is_like_building)
                        and      1 = case
                                       when p_complete_dataset = 'N' then 1
                                       when p_complete_dataset = 'Y' and
                                            p_normalized = 'N' and
                                            v_start_yyyymm      >= curr_year_start_yyyymm and
                                            v_end_yyyymm        <= curr_year_end_yyyymm and
                                            v_prev_start_yyyymm >= prev_year_start_yyyymm and
                                            v_prev_end_yyyymm   <= prev_year_end_yyyymm
                                       then 1
                                       when p_complete_dataset = 'Y' and
                                            p_normalized = 'Y' and
                                            v_start_yyyymm      >= curr_year_norm_start_yyyymm and
                                            v_end_yyyymm        <= curr_year_norm_end_yyyymm and
                                            v_prev_start_yyyymm >= prev_year_norm_start_yyyymm and
                                            v_prev_end_yyyymm   <= prev_year_norm_end_yyyymm
                                       then 1
                                       else 0
                                     end
                        and      (v_area_codes is null or regexp_count(area_code,v_area_codes) > 0)
                        and      (v_district_codes is null or regexp_count(district_code,v_district_codes) > 0)
                        and      (v_facility_ids is null or regexp_count(facility_id,v_facility_ids) > 0)
                        and      (station_type = p_station_type or p_station_type is null)
                        group by area_code, area_name, district_code, district_name, facility_id, facility_name, interior_sf,station_type)
              group by area_code, area_name, district_code, district_name, facility_id, facility_name,station_type);
      commit;
    end if;

    open p_cursor for
      select   area_code, area_name, district_code, district_name,
               facility_id, facility_name, sum_interior_sf, fac_count,
               sum_mon_kbtu_units, sply_kbtu_consump, sum_mon_cost, sply_cost, prev_kbtu, prev_cost
      from     uws_table_summary_cache_t
      where    start_year_in         = p_start_year
      and      end_year_in           = p_end_year
      and      start_month_in        = p_start_month
      and      end_month_in          = p_end_month
      and      (is_like_buildings_in = p_is_like_buildings
                or nvl(is_like_buildings_in,p_is_like_buildings) is null)
      and      normalized_in         = p_normalized
      and      complete_dataset_in   = p_complete_dataset
      and      (facility_id_in       = p_facility_id
                or nvl(facility_id_in,p_facility_id) is null)
      and      (area_code_in         = p_area_code
                or nvl(area_code_in,p_area_code) is null)
      and      (district_code_in     = p_district_code
                or nvl(district_code_in,p_district_code) is null)
      and      (station_type = p_station_type
               or nvl(station_type_in,p_station_type) is null)
      order by area_name, district_name, facility_name;
  end get_table_summary;

--------------------------------------------------------------------------------

  procedure table_summary_test as
    cur  table_summary_cursor;
    rec  table_summary_record;
    cnt  number := 0;
  begin
    get_table_summary(
      p_start_year        => 2012,
      p_end_year          => 2013,
      p_start_month       => 10,
      p_end_month         => 9,
      p_is_like_buildings => null,
      p_normalized        => 'N',
      p_complete_dataset  => 'N',
      p_facility_id       => null,
      p_area_code         => null,--'G'
      p_district_code     => null,--730,
      p_cursor            => cur
    );
    dbms_output.put_line('area | district | facility_id | fac_count | interior_sf |');
    dbms_output.put_line('-----|----------|-------------|-----------|-------------|');
    loop
      fetch cur into rec;
      exit when cur%notfound;
      cnt := cnt + 1;
      dbms_output.put(lpad(rec.area_code,4,' ')||' | ');
      dbms_output.put(lpad(nvl(rec.district_code,' '),8,' ')||' | ');
      dbms_output.put(lpad(nvl(rec.facility_id,' '),11,' ')||' | ');
      dbms_output.put(lpad(rec.fac_count,9,' ')||' | ');
      dbms_output.put(lpad(rec.sum_interior_sf,11,' ')||' | ');
      dbms_output.put_line(null);
    end loop;
    close cur;

    dbms_output.put_line(cnt||' records');

  end table_summary_test;

--------------------------------------------------------------------------------
/* Procedure to get Performance Rankings Data */

PROCEDURE get_performance_rankings(  p_start_year IN NUMBER,
                                p_end_year IN NUMBER,
                                p_start_month IN NUMBER,
                                p_end_month IN NUMBER,
                                p_is_like_buildings IN VARCHAR2,
                                p_normalized IN VARCHAR2,
                                p_complete_dataset IN VARCHAR2,
                                p_facility_id UWS_FACILITY_T.facility_id%TYPE default null,
                                p_area_code in varchar2 default null,
                                p_district_code in varchar2 default null,
                                P_CURSOR OUT C_CURSOR) IS

    V_CURSOR C_CURSOR;
    V_start_year_month number := case
                                  when p_start_month >= 10 then p_start_year + 1
                                  else p_start_year
                                 end||lpad(case
                                             when p_start_month >= 10 then p_start_month - 9
                                             else p_start_month + 3
                                           end,2,0);
    V_end_year_month number   := case
                                   when p_end_month >= 10 then p_end_year + 1
                                   else p_end_year
                                 end||lpad(case
                                             when p_end_month >= 10 then p_end_month - 9
                                             else p_end_month + 3
                                           end,2,0);

  begin

     -- National level supply calculation

             If  p_area_code is null and p_district_code is null and p_facility_id is null then

                 open V_CURSOR for

                     SELECT  area_code,
                             area_name,
                             sum_interior_sf,
                             fac_count,
                             sum_mon_kbtu_units,
                             decode(sum_mon_kbtu_units, 0, 0, round((1 - sum_prev_year_mon_kbtu_units / sum_mon_kbtu_units) * 100, 2)) sply_kbtu_consump,
                             sum_mon_cost,
                             decode(sum_mon_cost, 0, 0, round((1 - sum_prev_year_mon_cost / sum_mon_cost) * 100, 2)) sply_cost
                      FROM   (SELECT  area_code,
                                      area_name,
                                      SUM(interior_sf) AS sum_interior_sf,
                                      COUNT(facility_id) AS fac_count,
                                      SUM(sum_mon_kbtu_units) AS sum_mon_kbtu_units,
                                      SUM(sum_mon_cost) sum_mon_cost,
                                      SUM(sum_prev_year_mon_kbtu_units) AS sum_prev_year_mon_kbtu_units,
                                      SUM(sum_prev_year_mon_cost) AS sum_prev_year_mon_cost
                              FROM   (SELECT area_code,
                                             area_name,
                                             district_code,
                                             district_name,
                                             interior_sf,
                                             a.facility_id as facility_id,
                                             decode(p_normalized, 'Y', nvl(sum(mon_norm_kbtu_units), 0), SUM(MON_KBTU_UNITS)) AS sum_mon_kbtu_units,
                                             SUM(MON_COST) sum_mon_cost,
                                             decode(p_normalized, 'Y', NVL(sum(prev_year_mon_norm_kbtu_units), 0),(NVL(SUM(prev_year_mon_kbtu_units), 0))) AS sum_prev_year_mon_kbtu_units,
                                             NVL(SUM(prev_year_mon_cost), 0) AS sum_prev_year_mon_cost
                                      FROM uws_utility_monthly_fac_mv a
                                      left outer join uws_facility_func_t
                                      on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                                      left outer join FACILITY_SERVICE_RANGE_T
                                      on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                                      and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                                      and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                                      and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                                      where a.service_type in ('ELE', 'GAS', 'OIL', 'PPN', 'STEAM')
                                      AND to_date(FISCAL_YEAR_MONTH,'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                                      AND (a.facility_id = case
                                                             when p_is_like_buildings = null then a.facility_id
                                                             when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                             when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                           end

                                           or a.facility_id =  case
                                                                 when p_complete_dataset = 'Y'
                                                                     and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                                     and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                                 when p_complete_dataset = 'N' then a.facility_id
                                                               end
                                          )

                                      GROUP BY area_code, area_name, district_code, district_name, interior_sf, a.facility_id
                                     )
                              GROUP BY area_code, area_name
                            )
                     ORDER BY area_name, area_code;


    -- Area level supply calculation
            elsif p_district_code is null and p_area_code is not null and p_facility_id is null then

                  open V_CURSOR for

                      SELECT  area_code,
                              area_name,
                              district_code,
                              district_name,
                              sum_interior_sf,
                              fac_count,
                              sum_mon_kbtu_units,
                              decode(sum_mon_kbtu_units, 0, 0, round((1 - sum_prev_year_mon_kbtu_units / sum_mon_kbtu_units) * 100, 2)) sply_kbtu_consump,
                              sum_mon_cost,
                              decode(sum_mon_cost, 0, 0, round((1 - sum_prev_year_mon_cost / sum_mon_cost) * 100, 2)) sply_cost
                      FROM   ( SELECT area_code,
                                      area_name,
                                      district_code,
                                      district_name,
                                      SUM(interior_sf) AS sum_interior_sf,
                                      COUNT(facility_id) AS fac_count,
                                      SUM(sum_mon_kbtu_units) AS sum_mon_kbtu_units,
                                      SUM(sum_mon_cost) sum_mon_cost,
                                      SUM(sum_prev_year_mon_kbtu_units) AS sum_prev_year_mon_kbtu_units,
                                      SUM(sum_prev_year_mon_cost) AS sum_prev_year_mon_cost
                                FROM   (SELECT area_code,
                                               area_name,
                                               district_code,
                                               district_name,
                                               interior_sf,
                                               a.facility_id,
                                               decode(p_normalized, 'Y', nvl(sum(mon_norm_kbtu_units), 0), SUM(MON_KBTU_UNITS)) AS sum_mon_kbtu_units,
                                               SUM(MON_COST) sum_mon_cost,
                                               decode(p_normalized, 'Y', NVL(sum(prev_year_mon_norm_kbtu_units), 0),(NVL(SUM(PREV_YEAR_MON_kbtu_units), 0))) AS sum_prev_year_mon_kbtu_units,
                                               NVL(SUM(prev_year_mon_cost), 0) AS sum_prev_year_mon_cost
                                        FROM uws_utility_monthly_fac_mv a
                                        left outer join uws_facility_func_t
                                        on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                                        left outer join FACILITY_SERVICE_RANGE_T
                                        on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                                        and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                                        and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                                        and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                                        WHERE a.service_type IN ('ELE', 'GAS', 'OIL', 'PPN', 'STEAM')
                                        AND to_date(FISCAL_YEAR_MONTH,'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                                        AND area_code in( select regexp_substr(p_area_code,'[^,]+', 1, level) from dual connect by regexp_substr(p_area_code, '[^,]+', 1, level) is not null)
                                        AND (a.facility_id = case
                                                                when p_is_like_buildings = null then a.facility_id
                                                                when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                                when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                             end

                                              or  a.facility_id =  case
                                                                     when p_complete_dataset = 'Y'
                                                                       and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                                       and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                                     when p_complete_dataset = 'N' then a.facility_id
                                                                   end
                                            )

                                        GROUP BY area_code, area_name, district_code, district_name, interior_sf, a.facility_id
                                      )
                               GROUP BY area_code, area_name, district_code, district_name
                              )
                     ORDER BY area_name, district_name;

  -- District level supply  calculation
            elsif p_district_code is not null and p_area_code is not null and p_facility_id is null then

                  open v_cursor for

                     SELECT  facility_id,
                             facility_name,
                             area_code,
                             area_name,
                             district_code,
                             district_name,
                             SUM_INTERIOR_SF,
                             sum_mon_kbtu_units,
                             decode(sum_mon_kbtu_units,0,0,decode(sum_prev_year_mon_kbtu_units, 0,0,round((1-sum_prev_year_mon_kbtu_units/sum_mon_kbtu_units)*100,2))) sply_kbtu_consump,
                             sum_mon_cost,
                             decode(sum_mon_cost,0,0,decode(sum_prev_year_mon_cost, 0,0,round((1-sum_prev_year_mon_cost/sum_mon_cost) * 100,2))) sply_cost
                      FROM  (SELECT a.facility_id,
                                    facility_name,
                                    area_code,
                                    area_name,
                                    district_code,
                                    district_name,
                                    nvl(interior_sf,0) as SUM_INTERIOR_SF,
                                    decode(p_normalized, 'Y', nvl(sum(mon_norm_kbtu_units), 0), SUM(MON_KBTU_UNITS)) AS sum_mon_kbtu_units,
                                    SUM(mon_cost)sum_mon_cost,
                                    decode(p_normalized, 'Y', NVL(sum(prev_year_mon_norm_kbtu_units), 0),(NVL(SUM(prev_year_mon_kbtu_units), 0))) AS sum_prev_year_mon_kbtu_units,
                                    NVL(SUM(prev_year_mon_cost),0) AS sum_prev_year_mon_cost
                             FROM  uws_utility_monthly_fac_mv a
                             left outer join uws_facility_func_t
                             on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                             left outer join FACILITY_SERVICE_RANGE_T
                             on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                             and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                             and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                             and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                             WHERE a.service_type IN ('ELE','GAS','OIL','PPN','STEAM')
                             AND to_date(FISCAL_YEAR_MONTH,'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                             and  area_code in  ( select regexp_substr(p_area_code,'[^,]+', 1, level) from dual connect by regexp_substr(p_area_code, '[^,]+', 1, level) is not null)
                           --and  regexp_count(area_code,replace(p_area_code,',','|')) > 0
                             and  district_code in ( select regexp_substr(p_district_code,'[^,]+', 1, level) from dual connect by regexp_substr(p_district_code, '[^,]+', 1, level) is not null)
                           --and  regexp_count(district_code,replace(p_district_code,',','|')) > 0
                             AND (a.facility_id = case
                                                     when p_is_like_buildings = null then a.facility_id
                                                     when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                     when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                  end

                                  or a.facility_id = case
                                                       when p_complete_dataset = 'Y'
                                                         and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                         and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                       when p_complete_dataset = 'N' then a.facility_id
                                                     end
                                 )


                             GROUP BY a.facility_id, facility_name, area_code, area_name, district_code, district_name, nvl(interior_sf,0)
                            )
                      ORDER BY facility_name;

   -- For passing multiple values in the facility parameter

            elsif p_district_code is null and p_area_code is null and p_facility_id is not null then

                  open v_cursor for

                     SELECT  facility_id,
                             facility_name,
                             area_code,
                             area_name,
                             district_code,
                             district_name,
                             SUM_INTERIOR_SF,
                             sum_mon_kbtu_units,
                             decode(sum_mon_kbtu_units,0,0,decode(sum_prev_year_mon_kbtu_units, 0,0,round((1-sum_prev_year_mon_kbtu_units/sum_mon_kbtu_units)*100,2))) sply_kbtu_consump,
                             sum_mon_cost,
                             decode(sum_mon_cost,0,0,decode(sum_prev_year_mon_cost, 0,0,round((1-sum_prev_year_mon_cost/sum_mon_cost) * 100,2))) sply_cost
                      FROM  (SELECT a.facility_id,
                                    facility_name,
                                    area_code,
                                    area_name,
                                    district_code,
                                    district_name,
                                    nvl(interior_sf,0) as SUM_INTERIOR_SF,
                                    decode(p_normalized, 'Y', nvl(sum(mon_norm_kbtu_units), 0), SUM(MON_KBTU_UNITS)) AS sum_mon_kbtu_units,
                                    SUM(mon_cost)sum_mon_cost,
                                    decode(p_normalized, 'Y', NVL(sum(prev_year_mon_norm_kbtu_units), 0),(NVL(SUM(prev_year_mon_kbtu_units), 0))) AS sum_prev_year_mon_kbtu_units,
                                    NVL(SUM(prev_year_mon_cost),0) AS sum_prev_year_mon_cost
                             FROM  uws_utility_monthly_fac_mv a
                             left outer join uws_facility_func_t
                             on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                             left outer join FACILITY_SERVICE_RANGE_T
                             on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                             and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                             and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                             and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                             WHERE a.service_type IN ('ELE','GAS','OIL','PPN','STEAM')
                             AND to_date(FISCAL_YEAR_MONTH,'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                           --and  area_code in  ( select regexp_substr(p_area_code,'[^,]+', 1, level) from dual connect by regexp_substr(p_area_code, '[^,]+', 1, level) is not null)
                           --and  regexp_count(area_code,replace(p_area_code,',','|')) > 0
                           --and  district_code in ( select regexp_substr(p_district_code,'[^,]+', 1, level) from dual connect by regexp_substr(p_district_code, '[^,]+', 1, level) is not null)
                           --and  regexp_count(district_code,replace(p_district_code,',','|')) > 0
                             AND (a.facility_id = case
                                                     when p_is_like_buildings = null then a.facility_id
                                                     when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                     when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                  end

                                  or a.facility_id = case
                                                       when p_complete_dataset = 'Y'
                                                         and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                         and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                       when p_complete_dataset = 'N' then a.facility_id
                                                     end
                                 )

                             and a.facility_id in ( select regexp_substr(p_facility_id,'[^,]+', 1, level) from dual connect by regexp_substr(p_facility_id, '[^,]+', 1, level) is not null)
                             GROUP BY a.facility_id, facility_name, area_code, area_name, district_code, district_name, nvl(interior_sf,0)
                            )
                      ORDER BY facility_name;

            end if;

    P_CURSOR := V_CURSOR;

end   get_performance_rankings;


--------------------------------------------------------------------------------

  procedure get_monthly_by_util (p_utility_type          varchar2,
                                 p_facility_id           varchar2 default null,
                                 p_area_code             varchar2 default null,
                                 p_district_code         varchar2 default null,
                                 p_start_year            number,
                                 p_end_year              number,
                                 p_start_month           number,
                                 p_end_month             number,
                                 p_normalized            varchar2,
                                 p_is_like_buildings     varchar2,
                                 p_complete_dataset      varchar2,
                                 p_cursor            out monthly_util_cursor,
                                 p_show_area             char default null,
                                 p_show_district         char default null,
                                 p_show_facility         char default null,
                                 p_show_utility          char default null) as

    v_start_fy           number := p_start_year + floor(p_start_month / 10); -- Oct, Nov, Dec
    v_end_fy             number := p_end_year + floor(p_end_month / 10);     -- Oct, Nov, Dec
    v_start_fy_month     number := mod(p_start_month + 2, 12) + 1;
    v_end_fy_month       number := mod(p_end_month + 2, 12) + 1;
    v_start_yyyymm       number := v_start_fy || lpad(v_start_fy_month, 2, 0);
    v_end_yyyymm         number := v_end_fy || lpad(v_end_fy_month, 2, 0);
    v_prev_start_yyyymm  number := (v_start_fy - 1) || lpad(v_start_fy_month, 2, 0);
    v_prev_end_yyyymm    number := (v_end_fy - 1) || lpad(v_start_fy_month, 2, 0);

    v_show_area          char(1) := nvl(p_show_area,'Y');
    v_show_district      char(1) := case
                                      when p_show_district is not null then p_show_district
                                      when coalesce(p_area_code, p_district_code, p_facility_id) is null then 'N'
                                      else 'Y'
                                    end;
    v_show_facility      char(1) := case
                                      when p_show_facility is not null then p_show_facility
                                      when nvl(p_facility_id, p_district_code) is null then 'N'
                                      else 'Y'
                                    end;
    v_show_utility       char(1) := nvl(p_show_utility,'Y');

    v_area_codes         varchar2(4000) := replace(p_area_code,',','|');
    v_district_codes     varchar2(4000) := replace(p_district_code,',','|');
    v_facility_ids       varchar2(4000) := replace(p_facility_id,',','|');
    v_service_type       varchar2(4000) := replace(p_utility_type,',','|');
  begin
    update uws_monthly_util_cache_t
    set    use_date = sysdate
    where  start_year_in         = p_start_year
    and    end_year_in           = p_end_year
    and    start_month_in        = p_start_month
    and    end_month_in          = p_end_month
    and    (is_like_buildings_in = p_is_like_buildings
            or nvl(is_like_buildings_in,p_is_like_buildings) is null)
    and    normalized_in         = p_normalized
    and    complete_dataset_in   = p_complete_dataset
    and    (facility_id_in       = p_facility_id
            or nvl(facility_id_in,p_facility_id) is null)
    and    (area_code_in         = p_area_code
            or nvl(area_code_in,p_area_code) is null)
    and    (district_code_in     = p_district_code
            or nvl(district_code_in,p_district_code) is null)
    and    utility_type_in       = p_utility_type;


    if sql%rowcount = 0 then
      insert into uws_monthly_util_cache_t
      select sysdate,
             sysdate,
             p_start_year,
             p_end_year,
             p_start_month,
             p_end_month,
             p_utility_type,
             p_is_like_buildings,
             p_normalized,
             p_complete_dataset,
             p_facility_id,
             p_area_code,
             p_district_code,
             area_code,
             area_name,
             district_code,
             district_name,
             facility_id,
             facility_name,
             service_type,
             fiscal_year,
             fiscal_month,
             sum_interior_sq_ft,
             total_facility_count,
             sum_mon_kbtu_units,
             nvl(round((1-sum_prev_year_mon_kbtu_units/nullif(sum_mon_kbtu_units,0))*100,2),0) sply_kbtu_consump,
             sum_mon_cost,
             nvl(round((1-sum_prev_year_mon_cost/nullif(sum_mon_cost,0)) * 100,2),0) sply_cost
       FROM  ( select  area_code,
                       area_name,
                       district_code,
                       district_name,
                       facility_id,
                       facility_name,
                       fiscal_year,
                       fiscal_month,
                       service_type,
                       sum(decode(has_cc_data,'Y',interior_sf))   sum_interior_sq_ft,
                       count(decode(has_cc_data,'Y',1))           total_facility_count,
                       sum(sum_mon_kbtu_units)                    sum_mon_kbtu_units,
                       sum(sum_mon_cost)                          sum_mon_cost,
                       sum(sum_prev_year_mon_kbtu_units)          sum_prev_year_mon_kbtu_units,
                       sum(sum_prev_year_mon_cost)                sum_prev_year_mon_cost
             from ( select  decode(v_show_area,'Y',area_code) area_code,
                            decode(v_show_area,'Y',area_name) area_name,
                            decode(v_show_district,'Y',district_code) district_code,
                            decode(v_show_district,'Y',district_name) district_name,
                            decode(v_show_facility,'Y',facility_id) facility_id,
                            decode(v_show_facility,'Y',facility_name) facility_name,
                            fiscal_year,
                            fiscal_month,
                            decode(v_show_utility,'Y',service_type) service_type,
                            interior_sf,
                            max(has_cc_data) has_cc_data,
                            sum(decode(p_normalized,'Y',mon_norm_kbtu_units,mon_kbtu_units)) sum_mon_kbtu_units,
                            sum(mon_cost) sum_mon_cost,
                            sum(decode(p_normalized,'Y',prev_year_mon_norm_kbtu_units,prev_year_mon_kbtu_units)) sum_prev_year_mon_kbtu_units,
                            sum(prev_year_mon_cost) sum_prev_year_mon_cost
                from     uws_utility_monthly_fac_mv
                where    fiscal_year_month between v_start_yyyymm and v_end_yyyymm
                and      is_like_building = nvl(p_is_like_buildings,is_like_building)
                and      1 = case
                               when p_complete_dataset = 'N' then 1
                               when p_complete_dataset = 'Y' and
                                    p_normalized = 'N' and
                                    v_start_yyyymm      >= curr_year_start_yyyymm and
                                    v_end_yyyymm        <= curr_year_end_yyyymm and
                                    v_prev_start_yyyymm >= prev_year_start_yyyymm and
                                    v_prev_end_yyyymm   <= prev_year_end_yyyymm
                               then 1
                               when p_complete_dataset = 'Y' and
                                    p_normalized = 'Y' and
                                    v_start_yyyymm      >= curr_year_norm_start_yyyymm and
                                    v_end_yyyymm        <= curr_year_norm_end_yyyymm and
                                    v_prev_start_yyyymm >= prev_year_norm_start_yyyymm and
                                    v_prev_end_yyyymm   <= prev_year_norm_end_yyyymm
                               then 1
                               else 0
                             end
                and      (regexp_count(service_type,v_service_type) > 0)
                and      (v_area_codes is null or regexp_count(area_code,v_area_codes) > 0)
                and      (v_district_codes is null or regexp_count(district_code,v_district_codes) > 0)
                and      (v_facility_ids is null or regexp_count(facility_id,v_facility_ids) > 0)
                group by facility_id,facility_name,area_code, area_name, district_code, district_name, Fiscal_year, Fiscal_month, service_type, interior_sf
               )
               group by facility_id,facility_name,area_code, area_name, district_code, district_name, Fiscal_year, Fiscal_month, service_type
               )
          order by service_type;

      commit;
    end if;

    open p_cursor for
      select   area_code, area_name, district_code, district_name,facility_id,
               facility_name,service_type, fiscal_year, fiscal_month, sum_interior_sf,
               fac_count, sum_mon_kbtu_units, sply_kbtu_consump, sum_mon_cost, sply_cost
      from     uws_monthly_util_cache_t
      where    start_year_in         = p_start_year
      and      end_year_in           = p_end_year
      and      start_month_in        = p_start_month
      and      end_month_in          = p_end_month
      and      (is_like_buildings_in = p_is_like_buildings
                or nvl(is_like_buildings_in,p_is_like_buildings) is null)
      and      normalized_in         = p_normalized
      and      complete_dataset_in   = p_complete_dataset
      and      (facility_id_in       = p_facility_id
                or nvl(facility_id_in,p_facility_id) is null)
      and      (area_code_in         = p_area_code
                or nvl(area_code_in,p_area_code) is null)
      and      (district_code_in     = p_district_code
                or nvl(district_code_in,p_district_code) is null)
      and       utility_type_in      =  p_utility_type
      order by area_name, district_name, facility_name;


end get_monthly_by_util;

---------------------------------------------------------------------------------------------------
/* Procedure for the trend charts*/
PROCEDURE test_monthly_by_util ( p_utility_type IN VARCHAR2,
                                 p_facility_id IN UWS_FACILITY_T.FACILITY_ID%TYPE default null,
                                 p_area_code IN UWS_FACILITY_T.AREA_CODE%TYPE default null,
                                 p_district_code IN UWS_FACILITY_T.DISTRICT_CODE%TYPE default null,
                                 p_start_year IN NUMBER,
                                 p_end_year In NUMBER,
                                 p_start_month in number,
                                 p_end_month in number,
                                 p_normalized in varchar2,
                                 p_is_like_buildings in varchar2,
                                 p_complete_dataset in varchar2,
                                 P_CURSOR OUT C_CURSOR ) IS

        V_CURSOR C_CURSOR;
        V_start_year_month number := case
                                       when p_start_month >= 10 then p_start_year + 1
                                       else p_start_year
                                     end||lpad(case
                                                 when p_start_month >= 10 then p_start_month - 9
                                                 else p_start_month + 3
                                               end,2,0);
        V_end_year_month number   := case
                                       when p_end_month >= 10 then p_end_year + 1
                                       else p_end_year
                                     end||lpad(case
                                                 when p_end_month >= 10 then p_end_month - 9
                                                 else p_end_month + 3
                                               end,2,0);

    BEGIN

        If p_area_code is null and p_district_code is null and p_facility_id is null then

             OPEN v_cursor FOR

                    SELECT  area_code,
                            area_name,
                            fiscal_year,
                            fiscal_month,
                            sum_mon_kbtu_units,
                            service_type,
                            sum_mon_cost,
                            total_facility_count,
                            sum_interior_sq_ft
                    FROM   (SELECT  area_code,
                                    area_name,
                                    Fiscal_year,
                                    Fiscal_month,
                                    a.service_type,
                                    SUM(mon_kbtu_units)  AS sum_mon_kbtu_units,
                                    SUM(mon_cost)        AS sum_mon_cost,
                                    COUNT(a.facility_id) AS total_facility_count,
                                    SUM(interior_sf)     AS sum_interior_sq_ft
                            FROM uws_utility_monthly_fac_mv a
                            left outer join uws_facility_func_t
                            on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                            left outer join FACILITY_SERVICE_RANGE_T
                            on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                            and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                            and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                            and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                            WHERE a.service_type IN ('ELE','GAS','OIL','PPN','STEAM')
                            AND   to_date(fiscal_year||lpad(fiscal_month, 2,0),'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                            AND (a.facility_id = case
                                                   when p_is_like_buildings = null then a.facility_id
                                                   when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                   when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                 end

                                  or a.facility_id =  case
                                                        when p_complete_dataset = 'Y'
                                                            and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                            and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                        when p_complete_dataset = 'N' then a.facility_id
                                                      end
                                 )
                            AND a.service_type in (select regexp_substr(p_utility_type,'[^,]+', 1, level) from dual connect by regexp_substr(p_utility_type, '[^,]+', 1, level) is not null)
                            GROUP BY area_code,area_name,Fiscal_year,Fiscal_month,a.service_type
                           )
                    order by service_type;



        elsif p_area_code is  not null and p_district_code is null and p_facility_id is null then

             OPEN v_cursor FOR

                    SELECT  district_code,
                            district_name,
                            fiscal_year,
                            Fiscal_month,
                            service_type,
                            sum_mon_kbtu_units,
                            sum_mon_cost,
                            total_facility_count,
                            sum_interior_sq_ft
                    FROM  (SELECT district_code,
                                  district_name,
                                  Fiscal_year,
                                  Fiscal_month,
                                  a.service_type,
                                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                                  SUM(mon_cost)  AS sum_mon_cost,
                                  COUNT(a.FACILITY_ID) AS total_facility_count,
				                          SUM(interior_sf) AS sum_interior_sq_ft
                           FROM  uws_utility_monthly_fac_mv a
                           left outer join uws_facility_func_t
                           on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                           left outer join FACILITY_SERVICE_RANGE_T
                           on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                           and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                           and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                           and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                           WHERE a.service_type in (select regexp_substr(p_utility_type,'[^,]+', 1, level) from dual connect by regexp_substr(p_utility_type, '[^,]+', 1, level) is not null)
                           AND area_code = p_area_code
                           AND  to_date(fiscal_year||lpad(fiscal_month, 2,0),'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                           AND (a.facility_id = case
                                                  when p_is_like_buildings = null then a.facility_id
                                                  when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                  when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                end

                                or a.facility_id =  case
                                                      when p_complete_dataset = 'Y'
                                                        and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                        and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                      when p_complete_dataset = 'N' then a.facility_id
                                                    end
                                 )
                           group by district_code, district_name, Fiscal_year,Fiscal_month, a.service_type
                          );


        elsif p_area_code is null and p_district_code is not null and p_facility_id is null then

             OPEN v_cursor FOR

                    SELECT  facility_id,
                            facility_name,
                            area_code,
                            area_name,
                            district_code,
                            district_name,
                            fiscal_year,
                            fiscal_month,
                            sum_mon_kbtu_units,
                            service_type,
                            DECODE(sum_mon_kbtu_units,0,0,round((1-sum_prev_year_mon_kbtu_units/sum_mon_kbtu_units)*100,2)) sply_kbtu_consump,
                            sum_mon_cost,
                            DECODE(sum_mon_cost,0,0,round((1-sum_prev_year_mon_cost/sum_mon_cost) * 100,2)) sply_cost,
                            sum_interior_sq_ft
                    FROM  (SELECT a.facility_id,
                                  facility_name,
                                  area_code,
                                  area_name,
                                  district_code,
                                  district_name,
                                  Fiscal_year,
                                  Fiscal_month,
                                  a.service_type,
                                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                                  SUM(mon_cost)  AS sum_mon_cost,
                                  NVL(SUM(prev_year_mon_kbtu_units),0) AS sum_prev_year_mon_kbtu_units,
                                  NVL(SUM(prev_year_mon_cost),0) AS sum_prev_year_mon_cost,
                                  interior_sf AS sum_interior_sq_ft
                           FROM  uws_utility_monthly_fac_mv a
                           left outer join uws_facility_func_t
                           on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                           left outer join FACILITY_SERVICE_RANGE_T
                           on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                           and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                           and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                           and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                           WHERE a.service_type in  (select regexp_substr('ELE','[^,]+', 1, level) from dual connect by regexp_substr('ELE', '[^,]+', 1, level) is not null)
                           AND district_code = p_district_code
                           AND  to_date(fiscal_year||lpad(fiscal_month, 2,0),'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                           AND (a.facility_id = case
                                                  when p_is_like_buildings = null then a.facility_id
                                                  when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                  when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                end

                                or a.facility_id =  case
                                                      when p_complete_dataset = 'Y'
                                                         and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                         and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                      when p_complete_dataset = 'N' then a.facility_id
                                                    end
                                 )
                           group by a.facility_id,facility_name,area_code, area_name, district_code, district_name, Fiscal_year, Fiscal_month, a.service_type, interior_sf
                          )
                    order by service_type;



        elsif p_area_code is null and p_district_code is null and p_facility_id is not null then

             OPEN v_cursor FOR

                    SELECT  facility_id,
                            facility_name,
                            area_code,
                            area_name,
                            district_code,
                            district_name,
                            fiscal_year,
                            fiscal_month,
                            sum_mon_kbtu_units,
                            service_type,
                            DECODE(sum_mon_kbtu_units,0,0,round((1-sum_prev_year_mon_kbtu_units/sum_mon_kbtu_units)*100,2)) sply_kbtu_consump,
                            sum_mon_cost,
                            DECODE(sum_mon_cost,0,0,round((1-sum_prev_year_mon_cost/sum_mon_cost) * 100,2)) sply_cost,
                            sum_interior_sq_ft
                    FROM  (SELECT a.facility_id,
                                  facility_name,
                                  area_code,
                                  area_name,
                                  district_code,
                                  district_name,
                                  Fiscal_year,
                                  Fiscal_month,
                                  a.service_type,
                                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                                  SUM(mon_cost)  AS sum_mon_cost,
                                  NVL(SUM(prev_year_mon_kbtu_units),0) AS sum_prev_year_mon_kbtu_units,
                                  NVL(SUM(prev_year_mon_cost),0) AS sum_prev_year_mon_cost,
                                  interior_sf AS sum_interior_sq_ft
                           FROM  uws_utility_monthly_fac_mv a
                           left outer join uws_facility_func_t
                           on a.facility_id = uws_facility_func_t.facility_id and uws_facility_func_t.function_type = 'P'
                           left outer join FACILITY_SERVICE_RANGE_T on a.facility_id = FACILITY_SERVICE_RANGE_T.facility_id
                           and to_date(a.fiscal_year_month, 'yyyymm') between to_date(FACILITY_SERVICE_RANGE_T.START_YYYYMM, 'yyyymm') and to_date(FACILITY_SERVICE_RANGE_T.END_YYYYMM, 'yyyymm')
                           and a.service_type = FACILITY_SERVICE_RANGE_T.service_type
                           and p_normalized = FACILITY_SERVICE_RANGE_T.NORMALIZED
                           WHERE a.service_type in  (select regexp_substr(p_utility_type,'[^,]+', 1, level) from dual connect by regexp_substr(p_utility_type, '[^,]+', 1, level) is not null)
                           AND a.facility_id = p_facility_id
                           AND  to_date(fiscal_year||lpad(fiscal_month, 2,0),'yyyymm') between (to_date(v_start_year_month,'yyyymm')) and (to_date(v_end_year_month,'yyyymm'))
                           AND (a.facility_id = case
                                                  when p_is_like_buildings = null then a.facility_id
                                                  when p_is_like_buildings = 'Y' and function_type = 'P' then a.facility_id
                                                  when p_is_like_buildings = 'N' and function_type is null then a.facility_id
                                                end

                                or a.facility_id =  case
                                                      when p_complete_dataset = 'Y'
                                                        and add_months(to_date(v_start_year_month,'yyyymm'),-12) >= to_date(START_YYYYMM, 'yyyymm')
                                                        and to_date(v_end_year_month,'yyyymm') <= to_date(end_yyyymm,'yyyymm') then a.facility_id
                                                      when p_complete_dataset = 'N' then a.facility_id
                                                    end
                                 )
                          group by a.facility_id,facility_name,area_code, area_name, district_code, district_name, Fiscal_year, Fiscal_month, a.service_type, interior_sf
                         )
                    order by service_type;
        end if;

      P_CURSOR := V_CURSOR;

end test_monthly_by_util;



    PROCEDURE get_national_monthly_details (p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR ) AS
      BEGIN
        OPEN p_cursor FOR
			SELECT  area_code,
				area_name,
				fiscal_year,
				fiscal_month,
				sum_mon_kbtu_units,
				service_type,
				sum_mon_cost,
				total_facility_count,
				sum_interior_sq_ft
			FROM UWS_UTILITY_MONTHLY_NAT_MV
			WHERE fiscal_year between p_from_year AND p_to_year
			order by service_type;
    END;


    PROCEDURE get_area_monthly_details (p_area IN UWS_FACILITY_T.AREA_CODE%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR ) AS
      BEGIN
        OPEN p_cursor FOR
            SELECT area_code,
              area_name,
              district_code,
              district_name,
              fiscal_year,
              fiscal_month,
              sum_mon_kbtu_units,
              service_type,
              sum_mon_cost,
			  total_facility_count,
			  sum_interior_sq_ft
            FROM UWS_UTILITY_MONTHLY_AREA_MV
            WHERE fiscal_year between p_from_year AND p_to_year
              AND area_code = p_area
            order by service_type;
      END;



      PROCEDURE get_district_monthly_details (p_district_code IN UWS_FACILITY_T.DISTRICT_CODE%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR ) AS
      BEGIN
        OPEN p_cursor FOR
           SELECT  facility_id,
              facility_name,
              area_code,
              area_name,
              district_code,
              district_name,
              fiscal_year,
              fiscal_month,
              sum_mon_kbtu_units,
              service_type,
              sum_mon_cost,
			  total_facility_count,
			  sum_interior_sq_ft
          FROM UWS_UTILITY_MONTHLY_DIST_MV
            WHERE fiscal_year between p_from_year AND p_to_year
            AND district_code = p_district_code
            order by service_type;
      END;



      PROCEDURE get_facility_monthly_details (p_facility_id IN UWS_FACILITY_T.FACILITY_ID%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR ) as
      BEGIN
        OPEN p_cursor FOR
           SELECT  facility_id,
              facility_name,
              area_code,
              area_name,
              district_code,
              district_name,
              fiscal_year,
              fiscal_month,
              sum_mon_kbtu_units,
              service_type,
              DECODE(sum_mon_kbtu_units,0,0,round((1-sum_prev_year_mon_kbtu_units/sum_mon_kbtu_units)*100,2)) sply_kbtu_consump,
              sum_mon_cost,
              DECODE(sum_mon_cost,0,0,round((1-sum_prev_year_mon_cost/sum_mon_cost) * 100,2)) sply_cost,
              sum_interior_sq_ft
          FROM
          (SELECT facility_id,
                  facility_name,
                  area_code,
                  area_name,
                  district_code,
                  district_name,
                  Fiscal_year,
                  Fiscal_month,
                  service_type,
                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                  SUM(mon_cost)  AS sum_mon_cost,
                  NVL(SUM(prev_year_mon_kbtu_units),0) AS sum_prev_year_mon_kbtu_units,
                  NVL(SUM(prev_year_mon_cost),0) AS sum_prev_year_mon_cost,
				  interior_sf AS sum_interior_sq_ft
            FROM  uws_utility_monthly_fac_mv
            WHERE service_type IN ('ELE','GAS','OIL','PPN','STEAM')
              AND facility_id = p_facility_id
              AND  fiscal_year between p_from_year AND p_to_year
            group by facility_id,facility_name,area_code, area_name, district_code, district_name, Fiscal_year, Fiscal_month, service_type, interior_sf )
            order by service_type;
      END;


	PROCEDURE get_national_monthly_cost( p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR) AS
    BEGIN
      OPEN p_cursor FOR
           SELECT
              area_code,
              area_name,
              fiscal_year,
			  Fiscal_month,
              sum_mon_kbtu_units,
              sum_mon_cost,
			  total_facility_count,
			  sum_interior_sq_ft
          FROM
          (SELECT area_code,
                  area_name,
                  Fiscal_year,
				  Fiscal_month,
                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                  SUM(mon_cost)  AS sum_mon_cost,
				  COUNT(FACILITY_ID) AS total_facility_count,
				  SUM(interior_sf) AS sum_interior_sq_ft
            FROM  uws_utility_monthly_fac_mv
            WHERE service_type IN ('ELE','GAS','OIL','PPN','STEAM')
              AND  fiscal_year between p_from_year AND p_to_year
            group by area_code, area_name, Fiscal_year, Fiscal_month );
    END;

	PROCEDURE get_area_monthly_cost( p_area IN UWS_FACILITY_T.AREA_CODE%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR) AS
	BEGIN
      OPEN p_cursor FOR
           SELECT
              area_code,
              area_name,
			  district_code,
              district_name,
              fiscal_year,
			  Fiscal_month,
              sum_mon_kbtu_units,
              sum_mon_cost,
			  total_facility_count,
			  sum_interior_sq_ft
          FROM
          (SELECT area_code,
                  area_name,
				  district_code,
				  district_name,
                  Fiscal_year,
				  Fiscal_month,
                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                  SUM(mon_cost)  AS sum_mon_cost,
				  COUNT(FACILITY_ID) AS total_facility_count,
				  SUM(interior_sf) AS sum_interior_sq_ft
            FROM  uws_utility_monthly_fac_mv
            WHERE service_type IN ('ELE','GAS','OIL','PPN','STEAM')
              AND area_code = p_area
              AND  fiscal_year between p_from_year AND p_to_year
            group by area_code, area_name, district_code, district_name, Fiscal_year, Fiscal_month );
    END;

	PROCEDURE get_district_monthly_cost( p_district_code IN UWS_FACILITY_T.DISTRICT_CODE%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR ) AS
	BEGIN
      OPEN p_cursor FOR
           SELECT
              facility_id,
			  facility_name,
			  area_code,
              area_name,
              district_code,
              district_name,
              fiscal_year,
			  Fiscal_month,
              sum_mon_kbtu_units,
              sum_mon_cost,
			  total_facility_count,
			  sum_interior_sq_ft
          FROM
          (SELECT facility_id,
				  facility_name,
				  area_code,
                  area_name,
                  district_code,
                  district_name,
                  Fiscal_year,
				  Fiscal_month,
                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                  SUM(mon_cost)  AS sum_mon_cost,
				  COUNT(FACILITY_ID) AS total_facility_count,
				  SUM(interior_sf) AS sum_interior_sq_ft
            FROM  uws_utility_monthly_fac_mv
            WHERE service_type IN ('ELE','GAS','OIL','PPN','STEAM')
              AND district_code = p_district_code
              AND  fiscal_year between p_from_year AND p_to_year
            group by facility_id, facility_name, area_code, area_name, district_code, district_name, Fiscal_year,Fiscal_month );
    END;

	PROCEDURE get_facility_monthly_cost( p_facility_id IN UWS_FACILITY_T.FACILITY_ID%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR ) AS
	BEGIN
      OPEN p_cursor FOR
           SELECT
              facility_id,
              facility_name,
              area_code,
              area_name,
              district_code,
              district_name,
              fiscal_year,
			  Fiscal_month,
              sum_mon_kbtu_units,
              sum_mon_cost,
			  sum_interior_sq_ft
          FROM
          (SELECT facility_id,
                  facility_name,
                  area_code,
                  area_name,
                  district_code,
                  district_name,
                  Fiscal_year,
				  Fiscal_month,
                  SUM(mon_kbtu_units) AS sum_mon_kbtu_units,
                  SUM(mon_cost)  AS sum_mon_cost,
				  interior_sf AS sum_interior_sq_ft
            FROM  uws_utility_monthly_fac_mv
            WHERE service_type IN ('ELE','GAS','OIL','PPN','STEAM')
              AND facility_id = p_facility_id
              AND  fiscal_year between p_from_year AND p_to_year
            group by facility_id, facility_name, area_code, area_name, district_code, district_name, Fiscal_year, Fiscal_month, interior_sf );
    END;

    PROCEDURE get_national_yearly_summary( p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR) AS
    BEGIN
      OPEN p_cursor FOR
        SELECT COUNT(facility_id) AS total_facility_count
			, SUM(interior_sf) AS sum_interior_sq_ft
			, area_code
			, area_name
			, fiscal_year
			, SUM(sum_mon_kbtu_units) AS sum_mon_kbtu_units
			, SUM(sum_mon_cost) AS sum_mon_cost
		FROM (
			SELECT
				facility_id
				, interior_sf
				, area_code
				, area_name
				, Fiscal_year
				, SUM(ytd_kbtu_units) AS sum_mon_kbtu_units
				, SUM(ytd_cost) AS sum_mon_cost
			FROM UWS_UTILITY_YEARLY_FAC_MV
			WHERE service_type IN (
					'ELE'
					, 'GAS'
					, 'OIL'
					, 'PPN'
					, 'STEAM'
					)
				AND fiscal_year BETWEEN p_from_year AND p_to_year
			GROUP BY facility_id
				, interior_sf
				, area_code
				, area_name
				, Fiscal_year
			)
		GROUP BY area_code
			, area_name
			, fiscal_year;
    END;

    PROCEDURE get_area_yearly_summary( p_area IN UWS_FACILITY_T.AREA_CODE%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR) AS
    BEGIN
      OPEN p_cursor FOR
        SELECT COUNT(facility_id) AS total_facility_count
			, SUM(interior_sf) AS sum_interior_sq_ft
			, area_code
			, area_name
			, district_code
			, district_name
			, fiscal_year
			, SUM(sum_mon_kbtu_units) AS sum_mon_kbtu_units
			, SUM(sum_mon_cost) AS sum_mon_cost
		FROM (
			SELECT
				facility_id
				, interior_sf
				, area_code
				, area_name
				, district_code
				, district_name
				, Fiscal_year
				, SUM(ytd_kbtu_units) AS sum_mon_kbtu_units
				, SUM(ytd_cost) AS sum_mon_cost
			FROM UWS_UTILITY_YEARLY_FAC_MV
			WHERE service_type IN ('ELE', 'GAS', 'OIL', 'PPN', 'STEAM')
				AND area_code = p_area
				AND fiscal_year BETWEEN p_from_year
					AND p_to_year
			GROUP BY facility_id, interior_sf, area_code, area_name, district_code, district_name, Fiscal_year
			)
		GROUP BY area_code, area_name, district_code, district_name, fiscal_year;
    END;

    PROCEDURE get_district_yearly_summary( p_district_code IN UWS_FACILITY_T.DISTRICT_CODE%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR) AS
    BEGIN
      OPEN p_cursor FOR
           SELECT
              facility_id,
			  facility_name,
			  area_code,
              area_name,
              district_code,
              district_name,
              fiscal_year,
              sum_mon_kbtu_units,
              sum_mon_cost,
			  1 as total_facility_count,
			  interior_sf as sum_interior_sq_ft
          FROM
          (SELECT facility_id,
				  facility_name,
				  area_code,
                  area_name,
                  district_code,
                  district_name,
                  Fiscal_year,
                  SUM(ytd_kbtu_units) AS sum_mon_kbtu_units,
                  SUM(ytd_cost)  AS sum_mon_cost,
				  nvl(interior_sf,0) AS interior_sf
            FROM  UWS_UTILITY_YEARLY_FAC_MV
            WHERE service_type IN ('ELE','GAS','OIL','PPN','STEAM')
              AND district_code = p_district_code
              AND  fiscal_year between p_from_year AND p_to_year
            group by facility_id, facility_name, area_code, area_name, district_code, district_name, Fiscal_year, nvl(interior_sf,0));

    END;

    PROCEDURE get_facility_yearly_summary( p_facility_id IN UWS_FACILITY_T.FACILITY_ID%TYPE, p_from_year IN NUMBER, p_to_year In NUMBER, P_CURSOR OUT C_CURSOR) AS
    BEGIN
      OPEN p_cursor FOR
           SELECT
              facility_id,
              facility_name,
              area_code,
              area_name,
              district_code,
              district_name,
              fiscal_year,
              sum_mon_kbtu_units,
              sum_mon_cost,
			  sum_interior_sq_ft
          FROM
          (SELECT facility_id,
                  facility_name,
                  area_code,
                  area_name,
                  district_code,
                  district_name,
                  Fiscal_year,
                  SUM(ytd_kbtu_units) AS sum_mon_kbtu_units,
                  SUM(ytd_cost)  AS sum_mon_cost,
				  interior_sf AS sum_interior_sq_ft
            FROM  UWS_UTILITY_YEARLY_FAC_MV
            WHERE service_type IN ('ELE','GAS','OIL','PPN','STEAM')
              AND facility_id = p_facility_id
              AND  fiscal_year between p_from_year AND p_to_year
            group by facility_id, facility_name, area_code, area_name, district_code, district_name, Fiscal_year, interior_sf );
    END;
END;