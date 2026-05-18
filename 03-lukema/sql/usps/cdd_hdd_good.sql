SELECT
    SUM(cooling_day_temp_f)/SUM(cooling_day) as CDD,
    SUM(heating_day_temp_f)/SUM(heating_day) as HDD
FROM
    (
        SELECT
            CASE
                WHEN AVG_TEMP_F >= 65
                THEN AVG_TEMP_F
                ELSE 0
            END AS cooling_day_temp_f,
            CASE
                WHEN AVG_TEMP_F >= 65
                THEN 1
                ELSE 0
            END AS cooling_day,
            CASE
                WHEN AVG_TEMP_F < 65
                THEN AVG_TEMP_F
                ELSE 0
            END AS heating_day_temp_f,
            CASE
                WHEN AVG_TEMP_F < 65
                THEN 1
                ELSE 0
            END AS heating_day
        FROM
            UWS_FACILITY_WEATHER_T
        WHERE
            FACILITY_ID = '366353G18'
        AND PERIOD_DTM >= to_date('2012-10-01', 'yyyy-mm-dd') 
        AND PERIOD_DTM <= to_date('2013-09-30', 'yyyy-mm-dd'))