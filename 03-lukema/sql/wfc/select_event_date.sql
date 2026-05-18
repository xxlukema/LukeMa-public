SELECT
    MAX(IOA_BEGIN_DT) AS v_IOABeginDt
FROM
    T_CMSA_IOA_CALCULATION
WHERE
    LOAN_ID=110202647;
-- LOAN_ID=850201188;
--
SELECT
    MAX(EVENT_DT) AS v_dEventDt
FROM
    T_CMSA_INT_ON_ADVANCE
WHERE
    LOAN_ID=110202647; 
-- LOAN_ID=850201188;

-- in_dAdvanceDt = feed_dt (today)
--SELECT (in_dAdvanceDt - v_dEventDt) INTO v_nInterestDays FROM DUAL;
