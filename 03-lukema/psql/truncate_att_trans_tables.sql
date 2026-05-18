TRUNCATE TABLE "public"."billing_transaction" CASCADE;
--
TRUNCATE TABLE "public"."billing_record_transaction" CASCADE;
--
INSERT INTO billing_transaction (id, billing_file, last_sent_date) VALUES (12345, 'Rialtor.test.file.txt', '2015-04-18 03:30:00.562');
--
COMMIT;
