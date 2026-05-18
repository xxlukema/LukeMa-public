package com.learn.jul.util;


import java.util.logging.Level;
import java.util.logging.Logger;


public class LoggingApi {
    public static void doLog(final Logger LOG) {
        LOG.severe("Severe level.");
        LOG.warning("Warning level.");
        LOG.log(Level.INFO, "Parm 0: {0} Parm 1 2: {1}] {2}", new Object[] { "STRING ONE", "---String Two---", "===String 3===" });
        LOG.info("Info level.");
        LOG.config("Config level.");
        LOG.fine("Fine level.");
        LOG.finer("Finer level.");
        LOG.finest("Fineset level.");

        System.out.println("System out is here.");
        System.err.println("System err is here.");

        System.out.println("LOG.getName(): " + LOG.getName());
    }
}
