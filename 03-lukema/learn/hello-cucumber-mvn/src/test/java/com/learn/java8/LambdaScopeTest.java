package com.learn.java8;


import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class LambdaScopeTest {

    private static final Logger LOG = LogManager.getLogger();

    public int shadow = 0;

    class FirstLevel {

        public int shadow = 1;

        void methodInFirstLevel(int shadow) {

            // The following statement causes the compiler to generate
            // the error "local variables referenced from a lambda expression
            // must be final or effectively final" in statement A:
            //
            // shadow = 99;

            Consumer<Integer> myConsumer = (y) -> {
                LOG.info("shadow = " + shadow); // Statement A
                LOG.info("y = " + y);
                LOG.info("this.shadow = " + this.shadow);
                LOG.info("FirstLevel.this.shadow = " + FirstLevel.this.shadow);
                LOG.info("LambdaScopeTest.this.shadow = " + LambdaScopeTest.this.shadow);
            };

            myConsumer.accept(shadow);
        }
    }

    @Test
    public void testDoIntegerMath()
        throws Exception {
        LOG.info("Begin Test.");

        FirstLevel fl = new FirstLevel();
        fl.methodInFirstLevel(23);

        LOG.info("End Test.");
    }

}
