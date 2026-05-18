package com.learn.apple;


import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class HelloTest {

    // @Test
    public void runTest()
        throws Exception {
        log.info("Begin Test");

        String fileName = "initName.txt";

        log.info(fileName);

        log.info("End Test.");

    }

    @Test
    public void testArraySum2()
        throws Exception {
        log.info("Begin Test");

        // Integer[] myInts = { 2, 3, 4, 5, 6, 9, 8, 9, 8, 7, 8, 9 };

        log.info("End Test.");
    }

    // @Test
    public void testPalindrome()
        throws Exception {
        log.info("Begin Test");

        String str = "This is it. .ti si sihT";

        log.info(this.isPalindrome(str));
        log.info(this.isPalindrome(str + "L"));

        log.info("null == null ? {}", () -> null == null);

        log.info("End Test.");
    }

    private boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        int len = str.length();

        for (int i = 0; i < len / 2; i++) {
            char ch = str.charAt(i);
            char cchh = str.charAt(len - i - 1);

            if (ch != cchh) {
                return false;
            }
        }

        return true;
    }

    // @Test
    public void testArraySum()
        throws Exception {
        log.info("Begin Test");

        Integer[] myInts = { 2, 3, 4, 5, 6, 9, 8, 9, 8, 7, 8, 9 };

        Arrays.asList(myInts).stream().filter(item -> item >= 5).distinct().sorted((a, b) -> b - a).forEach(item -> log.info(item));

        Arrays.asList(myInts).stream().filter(item -> item >= 5).distinct().sorted(Comparator.reverseOrder()).forEach(item -> log.info(item));

        int sum = Arrays.asList(myInts).stream().parallel().filter(item -> item >= 5).distinct().reduce(0, (total, item) -> total + item,
                (total, item2) -> total + item2);

        log.info(sum);

        log.info("End Test.");

    }

    // @Test
    public void testFirstTwoDuplicate()
        throws Exception {
        log.info("Begin Test");

        String[] myArray = { "aaa", "bb", "bbv", "bb", "lll", "bbv", null, "aaa" };

        int counter = 0;

        for (int i = 0; i < myArray.length; i++) {
            String str = myArray[i];
            if (str == null) {
                continue;
            }

            for (int k = i + 1; k < myArray.length; k++) {
                if (str.equals(myArray[k])) {
                    counter++;

                    int ii = i;
                    int kk = k;
                    log.info("{}, {}, {}", () -> str, () -> ii, () -> kk);
                    if (counter >= 2) {
                        break;
                    }
                }
            }

            if (counter >= 2) {
                break;
            }
        }

        log.info("End Test.");

    }

}
