SELECT
    deptno,
    ename,
    sal,
    SUM(sal) over (partition BY deptno ORDER BY ename rows 2 preceding) sliding_total
FROM
    emp
ORDER BY
    deptno,
    ename