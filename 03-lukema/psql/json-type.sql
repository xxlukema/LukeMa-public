---
--- json type
---
CREATE TABLE orders (
	id serial NOT NULL PRIMARY KEY,
	info json NOT NULL
);
---
--- insert
---
INSERT INTO orders (info)
VALUES('{ "customer": "John Doe", "items": {"product": "Beer","qty": 6}}');
---
--- multiple insert
---
INSERT INTO orders (info)
VALUES('{ "customer": "Lily Bush", "items": {"product": "Diaper","qty": 24}}'),
      ('{ "customer": "Josh William", "items": {"product": "Toy Car","qty": 1}}'),
      ('{ "customer": "Mary Clark", "items": {"product": "Toy Train","qty": 2}}');
---
--- select into json
---
SELECT info -> 'customer' AS customer
FROM orders;
---
--- select into text
---
SELECT info ->> 'customer' AS customer
FROM orders;
---
--- select json.path
---
SELECT info -> 'items' ->> 'product' as product
FROM orders
ORDER BY product;
---
---
---
SELECT info ->> 'customer' AS customer
FROM orders
WHERE info -> 'items' ->> 'product' = 'Diaper';
---
---
---
SELECT info ->> 'customer' AS customer,
	info -> 'items' ->> 'product' AS product
FROM orders
WHERE CAST ( info -> 'items' ->> 'qty' AS INTEGER) = 2;
---
---
---
SELECT 
   MIN (CAST (info -> 'items' ->> 'qty' AS INTEGER)),
   MAX (CAST (info -> 'items' ->> 'qty' AS INTEGER)),
   SUM (CAST (info -> 'items' ->> 'qty' AS INTEGER)),
   AVG (CAST (info -> 'items' ->> 'qty' AS INTEGER))
FROM orders;
---
--- json_each
---
SELECT json_each (info)
FROM orders;
---
--- json_each_text
---
SELECT 
    json_data.key   AS key1,
    json_data.value AS value1
FROM 
    orders,
    json_each_text(orders.info) AS json_data;
---
--- json_each_text
---
SELECT 
    info
FROM 
    orders
WHERE 
    CAST (info -> 'items' ->> 'qty' AS INTEGER) = 2;
---
---
---
SELECT 
    info
FROM 
    orders
WHERE 
    CAST (info -> 'items' ->> 'qty' AS INTEGER) = 2;




