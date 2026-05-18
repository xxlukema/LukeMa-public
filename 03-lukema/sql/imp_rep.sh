#! /usr/bin/ksh


. db_env

dev_imp_table() {
   $ORACLE_HOME/bin/imp $dev_db TABLES=\(${1}\) File=${1}.dmp
}

prd_imp_table() {
   $ORACLE_HOME/bin/imp $prod_db TABLES=\(${1}\) File=${1}.dmp
}

dev_imp_table oa_rep
#prd_imp_table oa_afc


