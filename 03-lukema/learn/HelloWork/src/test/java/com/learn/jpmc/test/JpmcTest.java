package com.learn.jpmc.test;


import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class JpmcTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testMain()
        throws Exception {

        LOG.info("Begin Test.");

        Scanner scan = new Scanner(System.in);
        //scan.useDelimiter("\n");
        
        /*while(scan.hasNext()) {
            System.out.println(scan.next());
        }*/
        
        int i = scan.nextInt();
        double d = scan.nextDouble();
        String s = scan.next();

        scan.close();
        
        // Write your code here.

        System.out.println("String: " + s);
        System.out.println("Double: " + d);
        System.out.println("Int: " + i);

        LOG.info("End Test.");

    }
}
