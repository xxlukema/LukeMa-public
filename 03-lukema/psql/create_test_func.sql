---
CREATE OR REPLACE FUNCTION test_func
    () returns TABLE( id INTEGER,
                     create_date TIMESTAMP(6) WITHOUT TIME ZONE,
                     director CHARACTER VARYING(255),
                     movie_title CHARACTER VARYING(20),
                     update_date TIMESTAMP(6) WITHOUT TIME ZONE)
AS
    $aaa$
    SELECT
        *
    FROM
        movie $aaa$ LANGUAGE SQL;
---
SELECT
    *
FROM
    test_func();
---