# LC -197 - Raising Temperature
# Easy

# Runtime: 981 ms, faster than 10.97% of MySQL online submissions for Rising Temperature.
# Memory Usage: 0B, less than 100.00% of MySQL online submissions for Rising Temperature.

----------------------------
-- postgres
----------------------------
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
    w1.id
FROM
    weather AS w1
JOIN
    weather AS w2
ON
    DATEDIFF(w1.recordDate, w2.recordDate) = 1
AND w1.temperature > w2.temperature;

