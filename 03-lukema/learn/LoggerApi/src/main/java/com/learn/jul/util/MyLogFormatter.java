package com.learn.jul.util;


import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


/**
 * This formatter failed with parameterized logging:
 * LOG.log(Level.INFO, "Parm 0: {0} Parm 1 2: {1}] {2}", new Object[] { "STRING ONE", "---String Two---", "===String 3===" });
 * 
 */
@Deprecated
public class MyLogFormatter
    extends Formatter {
    @Override
    public String format(LogRecord record) {
        return "### " + Calendar.getInstance().getTime() + " " + record.getSourceClassName() + ": " + record.getSourceMethodName() + "()\n" + record.getLevel() + ": "
                + record.getMessage() + "\n";
    }

}
