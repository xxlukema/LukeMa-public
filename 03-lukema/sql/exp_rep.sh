#! /usr/bin/ksh


. db_env

dev_exp_table() {
   $ORACLE_HOME/bin/exp $dev_db TABLES=\(${1}\) File=${1}.dmp
}

prd_exp_table() {
   $ORACLE_HOME/bin/exp $prd_db TABLES=\(${1}\) File=${1}.dmp
}

#dev_exp_table oa_afc
prd_exp_table oa_rep


