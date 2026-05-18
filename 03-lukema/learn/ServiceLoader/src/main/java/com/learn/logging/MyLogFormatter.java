package com.learn.logging;


import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class MyLogFormatter
   extends Formatter
{
   @Override
   public String format(LogRecord record)
   {
      return Calendar.getInstance().getTime() + " " + record.getSourceClassName() + ": " + record.getSourceMethodName() + "()\n" + record.getLevel() + ": "
            + record.getMessage() + "\n";
   }

}
