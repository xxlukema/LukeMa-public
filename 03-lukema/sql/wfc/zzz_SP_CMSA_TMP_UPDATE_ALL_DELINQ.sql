CREATE OR REPLACE
PROCEDURE SP_CMSA_TMP_UPDATE_ALL_DELINQ
IS
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
BEGIN
  v_counter     := 0;
  v_counter_all := 0;
  OPEN cursor_loan_ids;
  LOOP
    FETCH cursor_loan_ids INTO v_loan_id;
    EXIT
  WHEN cursor_loan_ids%notfound;
    --v_loan_id := 310900868;
    SP_CMSA_TMP_UPDATE_ONE_DELINQ(IN_LOAN_ID => v_loan_id, OUT_ROWS_UPDATED => v_rows_updated);
    v_counter     := v_counter     + v_rows_updated;
    v_counter_all := v_counter_all + 1;
  END LOOP;
  CLOSE cursor_loan_ids;
  --
  DBMS_OUTPUT.put_line('Total Rows: ' || v_counter_all);
  DBMS_OUTPUT.put_line('Rows updated: ' || v_counter);
END;