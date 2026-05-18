--
SET serveroutput ON format wrapped;
--
CREATE OR REPLACE PROCEDURE tmp_supply_chain_test (
   V_LGA_NO              VARCHAR2,
   V_SCHEDULE_CODE       VARCHAR2,
   V_REQUEST_NO          NUMBER,
   V_TRANSACTION_TYPE    VARCHAR2
)
   IS
   
       -- V_LGA_NO              VARCHAR2(20) := '08085546XX0001';
       -- V_SCHEDULE_CODE       VARCHAR2(2)  := 'AA';
       -- V_REQUEST_NO          NUMBER       := '12019695';
       -- V_TRANSACTION_TYPE    VARCHAR2(10) := 'CREATE';
   
      CURSOR C1
      IS
         SELECT RG.AMT_RPT_DISB_REQ AMT_RPT_DISB_REQ,
                RG.AMT_RPT_PYMT_REQ AMT_RPT_PYMT_REQ,
                RG.DATE_DUE_REQ DATE_DUE_REQ,
                RG.ISN ISN,
                RG.ISN_REQUEST_REQ ISN_REQUEST_REQ
           FROM REQUEST R, REQUEST_GRP_SC RG
          WHERE R.ISN = RG.ISN_REQUEST_REQ
                AND R.NUM_REQ = V_REQUEST_NO
                AND NUM_LOAN_GUAR_REQ = V_LGA_NO
                AND CDE_SCHED_REQ = V_SCHEDULE_CODE;

      V_COUNTER              NUMBER := 0;
      V_INSTALL_ISN          NUMBER := 0;
      V_INSTALLMENT_NUMBER   NUMBER := 0;
   BEGIN
      FOR C IN C1
      LOOP
         BEGIN
            SELECT ISN
              INTO V_INSTALL_ISN
              FROM LG_INSTALLMENT LI
             WHERE LI.NUM_LOAN_GUAR_LGI = V_LGA_NO
                   AND LI.CDE_SCHED_LGI = V_SCHEDULE_CODE
                   AND LI.DTE_DUE_LGI = C.DATE_DUE_REQ;
         EXCEPTION
            WHEN OTHERS
            THEN
               V_INSTALL_ISN := 0;
         END;

         DBMS_OUTPUT.put_line('V_INSTALL_ISN: ' || V_INSTALL_ISN || ', C.DATE_DUE_REQ: ' || C.DATE_DUE_REQ );

         IF UPPER (V_TRANSACTION_TYPE) = 'CREATE'
         THEN
         
            DBMS_OUTPUT.put_line('V_TRANSACTION_TYPE: ' || V_TRANSACTION_TYPE); --- Create
         
            IF V_INSTALL_ISN = 0   --- New Record
            THEN
            
               DBMS_OUTPUT.put_line('New record.');
            
               /*
               SELECT LG_INSTALLMENT_ISN.NEXTVAL INTO V_INSTALL_ISN FROM DUAL;

               BEGIN
                  SELECT MAX (NUM_INS_LGI)
                    INTO V_INSTALLMENT_NUMBER
                    FROM LG_INSTALLMENT LI
                   WHERE LI.NUM_LOAN_GUAR_LGI = V_LGA_NO
                         AND LI.CDE_SCHED_LGI = V_SCHEDULE_CODE;
               EXCEPTION
                  WHEN OTHERS
                  THEN
                     V_INSTALLMENT_NUMBER := 0;
               END;

               V_INSTALLMENT_NUMBER := NVL (V_INSTALLMENT_NUMBER, 0) + 1;

               INSERT INTO LG_INSTALLMENT (ISN,
                                           NUM_LOAN_GUAR_LGI,
                                           CDE_SCHED_LGI,
                                           CDE_TYPE_LGI,
                                           NUM_INS_LGI,
                                           DTE_DUE_LGI,
                                           AMT_ORIG_PRIN_DUE_LGI,
                                           CDE_STOP_ASSUMED_REPYMT_LGI,
                                           AMT_ASSUMED_REPYMT_LGI)
                    VALUES (V_INSTALL_ISN,
                            V_LGA_NO,
                            V_SCHEDULE_CODE,
                            'P',
                            V_INSTALLMENT_NUMBER,
                            C.DATE_DUE_REQ,
                            C.AMT_RPT_DISB_REQ,
                            'Y',
                            C.AMT_RPT_PYMT_REQ);

               INSERT INTO LG_INSTALL_REPORTED (ISN,
                                                ISN_INSTALL_LGI,
                                                DTE_RPT_DISB_LGI,
                                                NUM_REQUEST_LGI,
                                                CDE_REQ_TYPE_LGI,
                                                AMT_RPT_DISB_LGI,
                                                AMT_RPT_PYMT_LGI)
                    VALUES (LG_INSTALL_REPORTED_ISN.NEXTVAL,
                            V_INSTALL_ISN,
                            TO_CHAR (SYSDATE, 'RRRRMMDD'),
                            V_REQUEST_NO,
                            'SC',
                            C.AMT_RPT_DISB_REQ,
                            C.AMT_RPT_PYMT_REQ);
                */
            ELSE
            
               DBMS_OUTPUT.put_line('Found record.');
               
               /*
               UPDATE LG_INSTALLMENT
                  SET AMT_ORIG_PRIN_DUE_LGI =
                         NVL (AMT_ORIG_PRIN_DUE_LGI, 0) + C.AMT_RPT_DISB_REQ,
                      AMT_ASSUMED_REPYMT_LGI =
                         NVL (AMT_ASSUMED_REPYMT_LGI, 0) + C.AMT_RPT_PYMT_REQ
                WHERE ISN = V_INSTALL_ISN;

               INSERT INTO LG_INSTALL_REPORTED (ISN,
                                                ISN_INSTALL_LGI,
                                                DTE_RPT_DISB_LGI,
                                                NUM_REQUEST_LGI,
                                                CDE_REQ_TYPE_LGI,
                                                AMT_RPT_DISB_LGI,
                                                AMT_RPT_PYMT_LGI)
                    VALUES (LG_INSTALL_REPORTED_ISN.NEXTVAL,
                            V_INSTALL_ISN,
                            TO_CHAR (SYSDATE, 'RRRRMMDD'),
                            V_REQUEST_NO,
                            'SC',
                            C.AMT_RPT_DISB_REQ,
                            C.AMT_RPT_PYMT_REQ);
                  */
            END IF;
         ELSE -- Not Create
         
            DBMS_OUTPUT.put_line('V_TRANSACTION_TYPE: ' || V_TRANSACTION_TYPE); --- Not Create 
            
            /*
            UPDATE LG_INSTALLMENT LI
               SET AMT_ORIG_PRIN_DUE_LGI =
                      NVL (AMT_ORIG_PRIN_DUE_LGI, 0) - C.AMT_RPT_DISB_REQ,
                   AMT_ASSUMED_REPYMT_LGI =
                      NVL (AMT_ASSUMED_REPYMT_LGI, 0) - C.AMT_RPT_PYMT_REQ
             WHERE LI.ISN = V_INSTALL_ISN;

            DELETE FROM LG_INSTALL_REPORTED
                  WHERE ISN_INSTALL_LGI = V_INSTALL_ISN
                        AND NUM_REQUEST_LGI = V_REQUEST_NO
                        AND DTE_RPT_DISB_LGI = TO_CHAR (SYSDATE, 'RRRRMMDD')
                        AND AMT_RPT_DISB_LGI = C.AMT_RPT_DISB_REQ
                        AND AMT_RPT_PYMT_LGI = C.AMT_RPT_PYMT_REQ;
            */
         END IF;
      END LOOP;
   END;