package com.learn.bbb;


import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;


public class TransactionIdTest {
    private static final Logger LOG = LogManager.getLogger();

    private static AtomicLong Counter = new AtomicLong(System.currentTimeMillis() / 1_000);

    private static int TransID_Len = 12;

    //@Ignore
    @Test
    public void testFiller()
        throws Exception {
        LOG.info("Begin Test");

        String transactionId = getFiller(3);

        LOG.info(transactionId);
        LOG.info(transactionId.length());

        LOG.info("End Test.");

    }

    @Ignore
    @Test
    public void runTest1()
        throws Exception {
        LOG.info("Begin Test");

        String orderId = "";
        String customerId = "CustId";
        String transactionQualifier = "12";
        String transactionTypeId = "MANAGE_CUSTOMER";

        String transactionId = getTransactionId(orderId, customerId, transactionQualifier, transactionTypeId);

        LOG.info(transactionId);
        LOG.info(transactionId.length());

        LOG.info("End Test.");

    }

    @Ignore
    @Test
    public void runTest2()
        throws Exception {
        LOG.info("Begin Test");

        String orderId = "";
        String customerId = "C";
        String transactionQualifier = "12";
        String transactionTypeId = "MANAGE_CUSTOMER";

        String transactionId = getTransactionId(orderId, customerId, transactionQualifier, transactionTypeId);

        LOG.info(Counter);
        LOG.info(transactionId);
        LOG.info(transactionId.length());

        LOG.info("End Test.");

    }

    @Ignore
    @Test
    public void runTest3()
        throws Exception {
        LOG.info("Begin Test");

        String orderId = "W12345";
        String customerId = "CustId";
        String transactionQualifier = "12abc";
        String transactionTypeId = "TransTypeId";

        String transactionId = getTransactionId(orderId, customerId, transactionQualifier, transactionTypeId);

        LOG.info(transactionId);
        LOG.info(transactionId.length());

        LOG.info("End Test.");

    }

    @Ignore
    @Test
    public void runTest4()
        throws Exception {
        LOG.info("Begin Test");

        String orderId = "W12345";
        String customerId = "CustId";
        String transactionQualifier = "12abcdefgh";
        String transactionTypeId = "TransTypeId";

        String transactionId = getTransactionId(orderId, customerId, transactionQualifier, transactionTypeId);

        LOG.info(transactionId);
        LOG.info(transactionId.length());

        LOG.info("End Test.");

    }

    public String getTransactionId(String orderId, String customerId, String transactionQualifier, String transactionTypeId) {
        // This is a special case where the transaction needs to be
        // generated from code.
        StringBuilder sb = new StringBuilder();
        if (orderId == null || orderId.isEmpty() && "MANAGE_CUSTOMER".equals(transactionTypeId)) {
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyD");
            //return String.format("%s-%s", customerId, dateFormat.format(new Date()));

            if (customerId != null && customerId.length() >= 2) {
                sb.append(customerId).append("-");
            }
        } else {
            sb.append(orderId).append("-");

            if (sb.length() + transactionQualifier.length() <= TransID_Len) {
                return sb.append(transactionQualifier).toString();
            }
        }

        String filler = getFiller(TransID_Len - sb.length());
        return sb.append(filler).toString();
    }

    private String getFiller(int len) {
        long number = Counter.getAndIncrement();
        String hex = Long.toHexString(number);

        int minLen = Math.min(TransID_Len, hex.length());

        int idx = Math.max(0, minLen - len);
        idx = Math.min(idx, hex.length());

        return hex.substring(idx);
    }
}
