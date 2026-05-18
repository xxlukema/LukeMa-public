CREATE OR REPLACE
PROCEDURE SP_CMSA_TMP_UPDATE_ONE_DELINQ(
    in_loan_id IN NUMBER,
    out_rows_updated OUT NUMBER)
IS
  v_comments T_CMSA_PROPERTY_STATUS.COMMENTS%TYPE;
  v_delinquent_comments T_CMSA_PERIODIC_LOAN.DELINQUENT_COMMENTS%TYPE;
  v_year  NUMBER;
  v_month NUMBER;
  v_ecode NUMBER;
  v_emesg VARCHAR2(2012);
BEGIN
  DBMS_OUTPUT.ENABLE(100000);
  --
  --in_loan_id := 310900868;
  --
  BEGIN
    SELECT comments
    INTO v_comments
    FROM
      (SELECT p.comments
      FROM T_CMSA_PROPERTY_STATUS P ,
        T_CMSA_LOAN_PROPERTY LP
      WHERE lp.property_id     = p.property_id
      AND p.property_status_cd = 2
      AND RTRIM(p.comments)   IS NOT NULL
      AND lp.loan_id           = in_loan_id
      ORDER BY p.STATUS_BEGIN_DT DESC
      )
    WHERE rownum = 1;
    --
    DBMS_OUTPUT.put_line('p.comments: ' || v_comments);
    --
    --
    SELECT PERIODIC_YEAR ,
      PERIODIC_MONTH,
      DELINQUENT_COMMENTS
    INTO v_year,
      v_month,
      v_delinquent_comments
    FROM
      (SELECT *
      FROM T_CMSA_PERIODIC_LOAN
      WHERE LOAN_ID                   = in_loan_id
      AND RTRIM(DELINQUENT_COMMENTS) IS NOT NULL
      ORDER BY ( PERIODIC_YEAR
        || lpad(PERIODIC_MONTH, 2, '0')) DESC
      )
    WHERE rownum = 1;
    --
    DBMS_OUTPUT.put_line('DELINQUENT_COMMENTS: ' || v_delinquent_comments);
    --
    -- ************************** --
    -- ************************** --
    -- ************************** --
    UPDATE T_CMSA_PERIODIC_LOAN
    SET DELINQUENT_COMMENTS = v_comments
    WHERE LOAN_ID           = in_loan_id
    AND PERIODIC_YEAR       = v_year
    AND PERIODIC_MONTH      = v_month;
    --
    COMMIT;
    -- ************************** --
    -- ************************** --
    -- ************************** --
    --
    DBMS_OUTPUT.put_line('Delinquent Comment updated for loan: ' || in_loan_id);
    out_rows_updated := 1;
    --
  EXCEPTION
  WHEN OTHERS THEN
    v_ecode := SQLCODE;
    v_emesg := SUBSTR(SQLERRM, 1, 2000);
    DBMS_OUTPUT.put_line(TO_CHAR(v_ecode) || '-' || v_emesg);
    --
    ROLLBACK;
    --
    DBMS_OUTPUT.put_line('###### Rollback completed for loan: ' || in_loan_id);
    out_rows_updated := 0;
    --
  END;
END;