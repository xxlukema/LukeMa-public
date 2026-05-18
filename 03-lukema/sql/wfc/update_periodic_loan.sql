SET serveroutput ON format wrapped;
CREATE OR REPLACE
PROCEDURE SP_CMSA_UPDATE_ONE_DELINQ_CMTS(
    in_loan_id NUMBER )
IS
  v_comments T_CMSA_PROPERTY_STATUS.COMMENTS%TYPE;
  v_delinquent_comments T_CMSA_PERIODIC_LOAN.DELINQUENT_COMMENTS%TYPE;
  v_year  NUMBER;
  v_month NUMBER;
  v_ecode NUMBER;
  v_emesg VARCHAR2(2000);
BEGIN
  --
  --in_loan_id := 310900868;
  --
  BEGIN
    SELECT p.comments
    INTO v_comments
    FROM T_CMSA_PROPERTY_STATUS P ,
      T_CMSA_LOAN_PROPERTY LP
    WHERE lp.property_id     = p.property_id
    AND p.property_status_cd = 2
    AND RTRIM(p.comments)   IS NOT NULL
    AND lp.loan_id           = in_loan_id;
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
      WHERE LOAN_ID            = in_loan_id
      AND DELINQUENT_COMMENTS IS NOT NULL
      ORDER BY ( PERIODIC_YEAR
        || lpad(PERIODIC_MONTH, 2, '0')) DESC
      )
    WHERE rownum = 1;
    DBMS_OUTPUT.put_line('DELINQUENT_COMMENTS: ' || v_delinquent_comments);
    --
    --
    UPDATE T_CMSA_PERIODIC_LOAN
    SET DELINQUENT_COMMENTS = v_comments
    WHERE LOAN_ID           = in_loan_id
    AND PERIODIC_YEAR       = v_year
    AND PERIODIC_MONTH      = v_month;
    --
    --
    COMMIT;
    --
    DBMS_OUTPUT.put_line('DELINQUENT_COMMENTS updated from ' || v_delinquent_comments || ' ==TO== ' || v_comments);
    --
  EXCEPTION
  WHEN OTHERS THEN
    v_ecode := SQLCODE;
    v_emesg := SQLERRM;
    DBMS_OUTPUT.put_line(TO_CHAR(v_ecode) || '-' || v_emesg);
    --
    ROLLBACK;
    --
    DBMS_OUTPUT.put_line('Rollback completed.');
    --
  END;
END;
