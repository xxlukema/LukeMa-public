CREATE OR REPLACE directory ext_dir AS 'C:\ora-ext-dir';
--
grant read, write on directory ext_dir to luke;
--
CREATE OR REPLACE directory ext_dir_err AS 'C:\ora-ext-err';
--
grant read, write on directory ext_dir_err to luke;
