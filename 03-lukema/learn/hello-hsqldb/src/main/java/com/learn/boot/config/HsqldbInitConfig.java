package com.learn.boot.config;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl;
import org.hsqldb.server.ServerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
public class HsqldbInitConfig {

    private static final String URL = "jdbc:hsqldb:hsql://localhost/xdb";
    private static final String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final String CREATE_SCHEMA_BMS = "CREATE SCHEMA IF NOT EXISTS BMS AUTHORIZATION SA";
    private static final String ALTER_USER = "ALTER USER SA SET INITIAL SCHEMA BMS";

    @Autowired
    public void autowired()
        throws ClassNotFoundException, SQLException {
        log.info(() -> "Inside HsqldbInitConfig.autowired()");

        /**
         * Trick!!
         *
         * Too late.
         *
         * Call inside `HelloHsqldbApplication.class`
         */
        // createSchemaBMS();
    }

    private static void createSchemaBMS()
        throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);

        try (Connection connection = DriverManager.getConnection(URL, "SA", "")) {

            PreparedStatement statement = connection.prepareStatement(CREATE_SCHEMA_BMS);

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                log.info("SQL Warning:");
                log.info("State  : " + warn.getSQLState());
                log.info("Message: " + warn.getMessage());
                log.info("Error  : " + warn.getErrorCode());
            }

            statement.executeUpdate();

            log.info(() -> "Created database schema 'BMS'.");

            statement = connection.prepareStatement(ALTER_USER);
            statement.executeUpdate();

            log.info(() -> "Altered user 'SA' to use 'MBS' as initial schema.");
        } catch (SQLException ex) {
            log.error("JDBC exception: {}", ex.getMessage(), ex);

            SQLException ex1 = ex;
            // Loop through the SQL Exceptions
            while (ex != null) {
                log.info("State  : " + ex.getSQLState());
                log.info("Message: " + ex.getMessage());
                log.info("Error  : " + ex.getErrorCode());

                ex = ex.getNextException();
            }

            throw ex1;
        }
    }

    private static final Server hsqldbServer = new Server();

    public static void startDBServer()
        throws IOException, ServerAcl.AclFormatException, ClassNotFoundException, SQLException {

        /**
         * Step 1 of 3: If hsqldb is running, skip this.
         */
        Class.forName(DRIVER);

        try (Connection connection = DriverManager.getConnection(URL, "SA", "")) {

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                log.info("SQL Warning:");
                log.info("State  : " + warn.getSQLState());
                log.info("Message: " + warn.getMessage());
                log.info("Error  : " + warn.getErrorCode());
            }

            log.info("hsqldb is running.");
            return;
        } catch (SQLException ex) {
            if (!"java.net.ConnectException: Connection refused: connect".equals(ex.getMessage())) {
                log.error("JDBC exception: {}", ex.getMessage(), ex);

                // Loop through the SQL Exceptions
                while (ex != null) {
                    log.info("State  : " + ex.getSQLState());
                    log.info("Message: " + ex.getMessage());
                    log.info("Error  : " + ex.getErrorCode());

                    ex = ex.getNextException();
                }
            }
        }

        /**
         * Step 2 of 3: If hsqldb is not running, start it.
         */
        HsqlProperties props = new HsqlProperties();
        props.setProperty("allow_empty_batch", true);
        props.setProperty("sql.syntax_pgs", true);

        props.setProperty("server.daemin", true);

        // props.setProperty("server.database.0", "mem:mydb;");
        hsqldbServer.setDatabasePath(0, "mem:mydb");

        // props.setProperty("server.dbname.0", "xdb");
        hsqldbServer.setDatabaseName(0, "xdb");

        /**
         * 9001 (normal) or 554 (if TLS encrypted)
         */
        // hsqldbServer.setPort(9001);

        try {
            hsqldbServer.setProperties(props);
            hsqldbServer.setTrace(true);
            hsqldbServer.setSilent(false);

            hsqldbServer.start();

            var desc = hsqldbServer.getStateDescriptor();
            log.info("hsqldb desc: {}", () -> desc);

            if (hsqldbServer.getState() != ServerConstants.SERVER_STATE_ONLINE) {
                log.error("hqsldb start error.", () -> hsqldbServer.getServerError());
            }

        } catch (IOException | ServerAcl.AclFormatException e) {
            log.error("Unable to start dqsldb: {}", () -> e.getMessage(), () -> e);
            throw e;
        }

        /**
         * Step 3 of 3. Create schema `BMS`.
         */
        HsqldbInitConfig.createSchemaBMS();
    }

    /*
    public void stopDBServer() {
        hsqldbServer.shutdown();
    }
    */

}
