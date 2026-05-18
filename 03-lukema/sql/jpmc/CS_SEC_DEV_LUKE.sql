--SET serveroutput ON

DECLARE
   TYPE user_type IS TABLE OF VARCHAR2 (100)
      INDEX BY BINARY_INTEGER;

   users_list   user_type;
   idx          BINARY_INTEGER;
   usr          VARCHAR2 (10);
   v_team_id    INTEGER;
BEGIN
   users_list (0) := 'I047215';

   
   SELECT team_id
     INTO v_team_id
     FROM cs_team
    WHERE description = 'COAST Operate/AD';

   FOR i IN users_list.FIRST .. users_list.LAST
   LOOP
      usr := UPPER (users_list (i));

      DELETE FROM cs_sec_user
            WHERE oracle_id = usr;

      INSERT INTO cs_sec_user
                  (oracle_id, first_name, last_name, description, team_id)
         SELECT id_standard, nm_first_upper, nm_last_upper,
                'Coast Developer', v_team_id
           FROM refdbo.v_workforce_jpmc_v3@link_rdr.world
          WHERE id_standard = usr
            AND SYSDATE BETWEEN cit_eff_asof_dt AND cit_eff_until_dt;

      DELETE FROM cs_sec_user_role
            WHERE user_name = usr;

      INSERT INTO cs_sec_user_role
           VALUES (usr, 'CEL Manager');

      INSERT INTO cs_sec_user_role
           VALUES (usr, 'User Security');

      INSERT INTO cs_sec_user_role
           VALUES (usr, 'CHDB_Manager');

      INSERT INTO cs_sec_user_role
           VALUES (usr, 'CEL Data Mgmt');

      INSERT INTO cs_sec_user_role
           VALUES (usr, 'CEL New Business');

      DBMS_OUTPUT.put_line (usr || ' added.');
   END LOOP;

   COMMIT;
END;
