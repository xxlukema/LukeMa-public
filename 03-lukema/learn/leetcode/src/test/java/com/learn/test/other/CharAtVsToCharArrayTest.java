package com.learn.test.other;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class CharAtVsToCharArrayTest {

    /**
     * 1 1453700
     * 2 540400
     * 3 644200
     * 0
     *
     *
     */
    @Test
    public void testCharAtVsToCharacterArray() {

        int tmp = 0;
        String s = new String(new byte[64 * 1024]);

        {
            long st = System.nanoTime();
            for (int i = 0, n = s.length(); i < n; i++) {
                tmp += s.charAt(i);
            }
            st = System.nanoTime() - st;
            log.debug("11111 vs 66666 improves speed by 44%: {}", String.format("%,d", st));
        }

        {
            long st = System.nanoTime();
            char[] chars = s.toCharArray();
            for (int i = 0, n = chars.length; i < n; i++) {
                tmp += chars[i];
            }
            st = System.nanoTime() - st;
            log.debug("22222 vs 44444 speed improved 23%: {}", String.format("%,d", st));
        }

        {
            long st = System.nanoTime();
            for (char c : s.toCharArray()) {
                tmp += c;
            }
            st = System.nanoTime() - st;
            log.debug("3: {}", String.format("%,d", st));
        }

        log.debug("{}", String.format("%,d", tmp));

        {
            float myFloat = 1234567892.007f;
            log.debug("Format float {}", String.format("%,.02f", myFloat));
        }

        {
            long st = System.nanoTime();
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                tmp += chars[i];
            }
            st = System.nanoTime() - st;
            log.debug("22222 vs 44444 speed down by 23%: {}", String.format("%,d", st));
        }

        {
            long st = System.nanoTime();
            for (int i = 0; i < s.length(); i++) {
                tmp += s.charAt(i);
            }
            st = System.nanoTime() - st;
            log.debug("11111 vs 66666 speed slow down 77%: {}", String.format("%,d", st));
        }
    }

}
