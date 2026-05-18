package com.learn.threaddump;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author xma
 * 
 * 
 * java -Xmn16M -Xmx32M -Xloggc:./GCLogs.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps 
 *      -XX:+HeapDumpOnOutOfMemoryError 
 *      -XX:HeapDumpPath=./LongSleep.hprof com.learn.LongSleepMemoryEater
 *      
 * java -Xms16M -Xmx32M Foo
 * 
 * ps ef
 * jps -mlv
 * jps
 * kill -QUIT <PID>
 * kill -3 <PID>
 * jstack -F -l <PID>
 * jstack -F <PID>
 * jstack <PID>
 * 
 * Typing Ctrl+Break is the correct way to generate a thread dump on Windows. 
 * 
 * jcmd.exe <PID> Thread.print   # same as 'jstatck <PID>' 
 * 
 * http://fastthread.io
 * 
 * 1. Total designated memory, this will equal the configured -Xmx value:
 *    32MB for java -Xms16M -Xmx32M Foo
 *    Runtime.getRuntime().maxMemory();
 * 
 * 2. Current allocated free memory, is the current allocated space ready for new objects. 
 *    Caution this is not the total free available memory:
 *    Runtime.getRuntime().freeMemory();
 *    
 * 3. Total allocated memory, is the total allocated space reserved for the java process:
 *    (between 16MB-32MB) for java -Xms16M -Xmx32M Foo
 *    Runtime.getRuntime().totalMemory();
 *    
 * 4. Used memory, has to be calculated:
 *    usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
 *    
 * 5. Total free memory, has to be calculated:
 *    freeMemory = Runtime.getRuntime().maxMemory() - usedMemory;
 * 
 * Runtime.getRuntime().maxMemory(); --- returns the -Xmx value
 * totalMemory() corresponds to the amount of memory currently available to the JVM for Foo
 * 
 *
 */
public class LongSleepMemoryEater {

    private static final String GroupedNumberNoDecimal = "#,###,###,##0";

    public static void main(String[] a) {

        printMemorySummary();

        List<String> list = new ArrayList<>();

        try {

            while (true) {

                for (int i = 0; i < 100; i++) {
                    list.add(new String("Hello Memory. Hello Memory. Hello Memory. Hello Memory. Hello Memory. Hello Memory. Hello Memory. "));
                }

                printMemorySummary();

                Thread.sleep(5_000);
                //Thread.sleep(1_000 * 60 * 60);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printMemorySummary() {

        Runtime rt = Runtime.getRuntime();

        long total = rt.totalMemory();
        long free = rt.freeMemory();
        long max = rt.maxMemory();

        long used = total - free;
        long avail = max - used;

        StringBuilder sb = new StringBuilder();
        sb.append(" Max memory (KB): \t").append(toGroupedNumberNoDecimal(max / 1_000)).append(System.lineSeparator());
        sb.append(" Total memory (KB): \t").append(toGroupedNumberNoDecimal(total / 1_000)).append(System.lineSeparator());
        sb.append(" Free memory (KB): \t").append(toGroupedNumberNoDecimal(free / 1_000)).append(System.lineSeparator());
        sb.append(" Used memory (KB): \t").append(toGroupedNumberNoDecimal(used / 1_000)).append(System.lineSeparator());
        sb.append(" Available memory (KB): \t").append(toGroupedNumberNoDecimal(avail / 1_000)).append(System.lineSeparator());

        System.out.println(sb.toString());
    }

    public static String toGroupedNumberNoDecimal(Number number) {
        if (number == null) {
            return "";
        }

        if (number.intValue() == 0) {
            return "0";
        }

        return new DecimalFormat(GroupedNumberNoDecimal).format(number);
    }

}
