#! /usr/bin/ksh


. db_env

dev_imp_table() {
   $ORACLE_HOME/bin/imp $dev_db TABLES=\(${1}\) File=${1}.dmp
}

prd_imp_table() {
   $ORACLE_HOME/bin/imp $prod_db TABLES=\(${1}\) File=${1}.dmp
}

home_imp_table() {
   $ORACLE_HOME/bin/imp $home_db TABLES=\(${1}\) GRANTS=N File=${1}.dmp
}

home_imp_table oa_cat_assoc


