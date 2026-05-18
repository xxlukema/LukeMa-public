--
SET serveroutput ON format wrapped;
--
DECLARE
  v_loan_id      NUMBER;
  v_counter      NUMBER;
  v_counter_all  NUMBER;
  v_rows_updated NUMBER;
  CURSOR cursor_loan_ids
  IS
    SELECT DISTINCT PL.LOAN_ID
    FROM T_CMSA_PROPERTY_STATUS P ,
      T_CMSA_LOAN_PROPERTY LP,
      T_CMSA_PERIODIC_LOAN PL
    WHERE LP.PROPERTY_ID               = P.PROPERTY_ID
    AND PL.LOAN_ID                     = LP.LOAN_ID
    AND P.PROPERTY_STATUS_CD           = 2
    AND RTRIM(p.COMMENTS)             IS NOT NULL
    AND RTRIM(pl.DELINQUENT_COMMENTS) IS NOT NULL;
  --
  v_comments T_CMSA_PROPERTY_STATUS.COMMENTS%TYPE;
  v_delinquent_comments T_CMSA_PERIODIC_LOAN.DELINQUENT_COMMENTS%TYPE;
  v_year  NUMBER;
  v_month NUMBER;
  v_ecode NUMBER;
  v_emesg VARCHAR2(2012);
BEGIN
  --
  DBMS_OUTPUT.put_line('Started...');
  --
  v_counter     := 0;
  v_counter_all := 0;
  DBMS_OUTPUT.ENABLE(2000000);
  --
  OPEN cursor_loan_ids;
  LOOP
    FETCH cursor_loan_ids INTO v_loan_id;
    EXIT
  WHEN cursor_loan_ids%notfound;
    --v_loan_id := 310900868;
    --SP_CMSA_TMP_UPDATE_ONE_DELINQ(IN_LOAN_ID => v_loan_id, V_ROWS_UPDATED => v_rows_updated);
    --
    DBMS_OUTPUT.put_line('Loan_id: ' || v_loan_id);
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
        AND lp.loan_id           = v_loan_id
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
        WHERE LOAN_ID                   = v_loan_id
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
      INSERT
      INTO CMSA_APP_USER.T_CMSA_PERIODIC_LOAN_BAK
        (
          LOAN_ID,
          PERIODIC_YEAR,
          PERIODIC_MONTH,
          DELINQUENT_COMMENTS_OLD,
          DELINQUENT_COMMENTS_NEW,
          BAK_DATE
        )
        VALUES
        (
          v_loan_id,
          v_year,
          v_month,
          v_delinquent_comments,
          v_comments,
          sysdate
        );
      --
      
      UPDATE T_CMSA_PERIODIC_LOAN
      SET DELINQUENT_COMMENTS = v_comments
      WHERE LOAN_ID           = v_loan_id
      AND PERIODIC_YEAR       = v_year
      AND PERIODIC_MONTH      = v_month;
      
      --
      COMMIT;
      -- ************************** --
      -- ************************** --
      -- ************************** --
      --
      DBMS_OUTPUT.put_line('Delinquent Comment updated for loan: ' || v_loan_id);
      v_rows_updated := 1;
      --
    EXCEPTION
    WHEN OTHERS THEN
      v_ecode := SQLCODE;
      v_emesg := SUBSTR(SQLERRM, 1, 2000);
      DBMS_OUTPUT.put_line(TO_CHAR(v_ecode) || ': ' || v_emesg);
      --
      ROLLBACK;
      --
      DBMS_OUTPUT.put_line('###### Rollback completed for loan: ' || v_loan_id);
      v_rows_updated := 0;
      --
    END;
    --
    v_counter     := v_counter     + v_rows_updated;
    v_counter_all := v_counter_all + 1;
  END LOOP;
  CLOSE cursor_loan_ids;
  --
  DBMS_OUTPUT.put_line('Total Rows: ' || v_counter_all);
  DBMS_OUTPUT.put_line('Rows updated: ' || v_counter);
END;
