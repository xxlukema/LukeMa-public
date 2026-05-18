
--DROP PROCEDURE proc_query_products

CREATE PROCEDURE proc_query_products(
   IN  inPrice Float,
   OUT outName varchar(12),
   OUT outPrice Float
)
BEGIN
   SELECT name, price
   into outName, outPrice
   FROM products
   --WHERE price > inPrice
END


--select name, price from products




