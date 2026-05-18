INSERT
INTO
    T_CMSA_INT_ON_ADVANCE
    (
        INVESTOR_ID ,
        LOAN_ID ,
        EVENT_DT ,
        PI_INTEREST ,
        TI_INTEREST ,
        SERVICING_INTEREST ,
        INTEREST_DAYS ,
        PRIME_RATE
    )
    VALUES
    (
        568 ,
        110202647 ,
        to_date('30-APR-12', 'dd-mm-yy') ,
        0,
        0,
        0,
        1231 ,
        .0325
    )
---
INSERT
INTO
    T_CMSA_INT_ON_ADVANCE
    (
        INVESTOR_ID ,
        LOAN_ID ,
        EVENT_DT ,
        PI_INTEREST ,
        TI_INTEREST ,
        SERVICING_INTEREST ,
        INTEREST_DAYS ,
        PRIME_RATE
    )
    VALUES
    (
        612 ,
        850201188 ,
        to_date('30-APR-12', 'dd-mm-yy') ,
        0,
        456.8,
        0,
        1231 ,
        .0325
    )    
    