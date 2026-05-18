SELECT
    SUM(cooling_day_temp_f) AS CDD,
    SUM(heating_day_temp_f) AS HDD
FROM
    (
        SELECT
            CASE
                WHEN AVG_TEMP_F >= 75
                THEN AVG_TEMP_F - 75
                ELSE 0
            END AS cooling_day_temp_f,
            CASE
                WHEN AVG_TEMP_F < 55
                THEN 55 - AVG_TEMP_F
                ELSE 0
            END AS heating_day_temp_f
        FROM
            UWS_FACILITY_WEATHER_T
        WHERE
            FACILITY_ID = '366353G18'
        AND PERIOD_DTM >= to_date('2012-10-01', 'yyyy-mm-dd')
        AND PERIOD_DTM <= to_date('2013-09-30', 'yyyy-mm-dd'))