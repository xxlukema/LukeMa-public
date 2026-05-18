SELECT
    TMP.Servicing_Dt                                         SERVICING_DT,
    PIA.Advance_Dt                                           PI_Advance_Dt,
    NVL(PIA.Principal_Advance,0)                             AS PRINCIPAL_ADVANCE,
    NVL(PIA.Interest_Advance,0)                              AS INTEREST_ADVANCE,
    NVL(PIA.Net_Interest,0)                                  AS NET_INTEREST,
    (NVL(PIA.Principal_Advance,0) + NVL(PIA.Net_Interest,0))                        AS MAX_PI,
    (NVL(PIA.Principal_Advance,0) + NVL(PIA.Net_Interest,0) - NVL(PIA.Actual_PI,0)) AS ASER_AMT,
    NVL(PIA.Actual_PI,0)                                                            AS ACTUAL_PI,
    NVL(PIA.ASER_Percnt,0)                                                          AS ASER_PERCNT,
    NVL(PIR.PIRecovery,0)                                                           AS PI_RECOVERY,
    NVL(SERVA.Servicing_Advance,0)                                                     Servicing_ADVANCE
    ,
    NVL(SERVR.Servicing_Recovery,0) Servicing_RECOVERY,
    NVL(TIA.TI_Advance,0)           TI_ADVANCE,
    NVL(TIR.TI_Recovery,0)          TI_RECOVERY
FROM
    (
        SELECT
            Investor_Id,
            Loan_Id,
            Advance_Dt AS Servicing_Dt
        FROM
            T_CMSA_PI_ADVANCE
        WHERE
            Investor_Id = 568
        AND Loan_Id = 110202647
        AND Advance_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        UNION
        SELECT
            Investor_Id,
            Loan_Id,
            Recovery_Dt AS Servicing_Dt
        FROM
            T_CMSA_PI_RECOVERY
        WHERE
            Investor_Id = 568
        AND Loan_Id = 110202647
        AND Recovery_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        UNION
        SELECT
            Investor_Id,
            Loan_Id,
            Advance_Dt AS Servicing_Dt
        FROM
            T_CMSA_TI_ADVANCE
        WHERE
            Investor_Id = 568
        AND Loan_Id = 110202647
        AND Advance_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        UNION
        SELECT
            Investor_Id,
            Loan_Id,
            Recovery_Dt AS Servicing_Dt
        FROM
            T_CMSA_TI_RECOVERY
        WHERE
            Investor_Id = 568
        AND Loan_Id = 110202647
        AND Recovery_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        UNION
        SELECT
            Investor_Id,
            Loan_Id,
            Advance_Dt AS Servicing_Dt
        FROM
            T_CMSA_SERV_ADVANCE
        WHERE
            Investor_Id = 568
        AND Loan_Id = 110202647
        AND Advance_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        UNION
        SELECT
            Investor_Id,
            Loan_Id,
            Recovery_Dt AS Servicing_Dt
        FROM
            T_CMSA_SERV_RECOVERY
        WHERE
            Investor_Id = 568
        AND Loan_Id = 110202647
        AND Recovery_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        UNION
        SELECT
            568,
            110202647,
            Rate_Change_Dt AS Servicing_Dt
        FROM
            T_CMSA_PRIME_RATE
        WHERE
            Rate_Change_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        UNION
        SELECT
            568,
            110202647,
            Recovery_Dt AS Servicing_Dt
        FROM
            T_CMSA_INT_RECOVERY
        WHERE
            Investor_Id = 568
        AND Loan_Id = 110202647
        AND Recovery_Dt >= to_date('2012-04-30', 'yyyy-mm-dd') ) TMP,
    T_CMSA_PI_ADVANCE PIA,
    (
        SELECT
            R.Investor_Id,
            R.Loan_Id,
            R.Recovery_Dt,
            SUM( ROUND((Principal_Recovery + Interest_Recovery) - ( ((Principal_Recovery +
            Interest_Recovery) * (A.Principal_Advance + A.Interest_Advance - A.Actual_PI)) /
            (A.Principal_Advance + A.Interest_Advance) ),2) ) PIRecovery,
            SUM(Fee_Recovery)                                 FeeRecovery
        FROM
            T_CMSA_PI_RECOVERY R,
            T_CMSA_PI_ADVANCE A
        WHERE
            R.Investor_Id = A.Investor_Id
        AND R.Loan_Id = A.Loan_Id
        AND R.Payment_Due_Dt = A.Payment_Due_Dt
        AND R.Investor_Id = 568
        AND R.Loan_Id = 110202647
        AND R.Recovery_Dt >= to_date('2012-04-30', 'yyyy-mm-dd')
        GROUP BY
            R.Investor_Id,
            R.Loan_Id,
            R.Recovery_Dt) PIR,
    T_CMSA_SERV_ADVANCE SERVA,
    T_CMSA_SERV_RECOVERY SERVR,
    T_CMSA_TI_ADVANCE TIA,
    T_CMSA_TI_RECOVERY TIR
WHERE
    TMP.Investor_Id = PIA.Investor_Id(+)
AND TMP.Loan_Id = PIA.Loan_Id(+)
AND TMP.Servicing_Dt = PIA.Advance_Dt(+)
AND TMP.Investor_Id = PIR.Investor_Id(+)
AND TMP.Loan_Id = PIR.Loan_Id(+)
AND TMP.Servicing_Dt = PIR.Recovery_Dt(+)
AND TMP.Investor_Id = SERVA.Investor_Id(+)
AND TMP.Loan_Id = SERVA.Loan_Id(+)
AND TMP.Servicing_Dt = SERVA.Advance_Dt(+)
AND TMP.Investor_Id = SERVR.Investor_Id(+)
AND TMP.Loan_Id = SERVR.Loan_Id(+)
AND TMP.Servicing_Dt = SERVR.Recovery_Dt(+)
AND TMP.Investor_Id = TIA.Investor_Id(+)
AND TMP.Loan_Id = TIA.Loan_Id(+)
AND TMP.Servicing_Dt = TIA.Advance_Dt(+)
AND TMP.Investor_Id = TIR.Investor_Id(+)
AND TMP.Loan_Id = TIR.Loan_Id(+)
AND TMP.Servicing_Dt = TIR.Recovery_Dt(+)
ORDER BY
    TMP.Servicing_Dt ;
