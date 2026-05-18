CREATE FUNCTION vrd_guff.luke_func(uname TEXT, pass TEXT)
RETURNS TEXT AS $$
DECLARE res TEXT;
BEGIN
        SELECT  uname || ' ' || pass INTO res;
 
        RETURN res;
END;
$$  LANGUAGE plpgsql