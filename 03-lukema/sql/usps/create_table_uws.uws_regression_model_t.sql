CREATE TABLE
    uws.uws_regression_model_t
    (
        regression_model_id NUMBER(12) NOT NULL,
        type_of_quarter VARCHAR2(40),
        average_square_footage NUMBER(12),
        Number_of_pcs NUMBER(8),
        number_of_workers NUMBER (8),
        weekly_operating_hours NUMBER(8),
        percentage_heated NUMBER(3),
        percentage_cooled NUMBER(3),
        CONSTRAINT PFK_uws_regression_model_t PRIMARY KEY (regression_model_id)
    );
--
