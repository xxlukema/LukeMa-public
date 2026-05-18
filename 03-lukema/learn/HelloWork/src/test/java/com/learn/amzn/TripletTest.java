package com.learn.amzn;


import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class TripletTest {
    private static final Logger LOG = LogManager.getLogger();

    //private static final String QuantityFormat = "######0.00";

    @Test
    public void testMain()
        throws Exception {
        LOG.info("Begin Test.");

        final int[] a = { 1, 8, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * a[i];
        }

        Arrays.sort(a);

        for (int i = 0; i < a.length - 2; i++) {
            int a2 = a[i];
            for (int k = i + 1; k < a.length - 1; k++) {
                int b2 = a[k];
                for (int m = k + 1; m < a.length; m++) {
                    int c2 = a[m];
                    if (a2 + b2 == c2) {
                        LOG.info((int) Math.sqrt(a2) + " " + (int) Math.sqrt(b2) + " -> " + (int) Math.sqrt(c2));
                    }
                }
            }
        }

        LOG.info("End Test.");
    }
}
