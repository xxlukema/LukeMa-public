------------------------------
-- postgres
------------------------------
SELECT
    w1.id
FROM
    weather AS w1
JOIN
    weather AS w2
ON
    DATE_PART('day', to_date(w1.recorddate, 'yyyy-mm-dd')) - DATE_PART('day', to_date 
    (w2.recorddate, 'yyyy-mm-dd')) = 1
AND w1.temperature > w2.temperature;
----------------------------
-- MySQL
----------------------------
SELECT
    weather.id AS 'Id'
FROM
    weather
JOIN
    weather w
ON
    DATEDIFF(weather.recordDate, w.recordDate) = 1
AND weather.Temperature > w.Temperature;