UPDATE
    T_CMSA_PROPERTY_APPRAISAL
SET
    APPRAISAL_DT = to_date('2010-11-16', 'yyyy-mm-dd'),
    REMARKS =
    'Appraisal date was 2012-nov-16, which is a future date of today 2012-may-02. Change it to 2010-nov-16 to correctly reflect appraisal value on report. Change requested by thanhlo'
WHERE
    PROPERTY_ID = 13199
AND APPRAISAL_DT = to_date('2012-11-16', 'yyyy-mm-dd');