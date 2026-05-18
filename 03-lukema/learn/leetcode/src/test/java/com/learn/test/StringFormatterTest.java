package com.learn.test;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StringFormatterTest {

    /**
     * https://dzone.com/articles/java-string-format-examples
     *
     * c: Character
     * d: decimal integer
     * e: floating point decimal in scientific notation
     * f: floating point decimal
     * o: octal
     * s: any type. output string
     * t: Date/Time in lowercase
     * T: Date/Time in uppercase
     * x: hex in lowercase
     * X: hex in uppercase
     *
     * +: with sign
     * -: left align
     * ,: decimal with scientific grouping
     * 10: 10 chars prepending with 0s
     * 010: octal 10 chars
     * % d: decimal prepending with space ' '
     */
    @Test
    public void testFormat() {

        log.debug(String.format("%d", 93)); // prints 93
        // "s" is for any type
        log.debug(String.format("%s", 93)); // prints 93
        log.debug(String.format("|%6d|", 93)); // prints: |    93|
        log.debug(String.format("|%-6d|", 93)); // prints: |93    |
        log.debug(String.format("|%06d|", 93)); // prints: |000093|
        log.debug(String.format("|%+6d|", 93)); // prints: |   +93|
        log.debug(String.format("|% d|", 93)); // prints: | 93|
        log.debug(String.format("|% d|", -36)); // prints: |-36|

        // total 6 chars including sign, dot '.', and parenthesis "()". If too many digits, auto expend
        log.debug(String.format("|% 6.2f|", -1234567.1415926)); // prints: |-36|
        log.debug(String.format("|% 6.2f|", -3.1415926)); // prints: |-36|
        log.debug(String.format("|%06.2f|", -3.1415926)); // prints: |-36|
        log.debug(String.format("|% (6.2f|", -3.1415926)); // prints: |-36|
        // auto expend length
        log.debug(String.format("|% (6.2f|", -13.1415926)); // prints: |-36|

        log.debug(String.format("|%,d|", 10000000)); // prints: |10,000,000|
        // Enclose negative numbers within parentheses (“()”) and skip the "-":
        log.debug(String.format("|%(d|", -36)); // prints: |(36)|

        // 93 in octal with no leading 0
        log.debug(String.format("|%o|", 93)); // prints: 135, 93
        // 93 in hex lowercase with no leading "0x"
        log.debug(String.format("|%x|", 93)); // prints: 5d, 93
        // with leading "0"
        log.debug(String.format("|%#o|", 93)); // prints: 0135, 93
        // with leading "0x" lowercase
        log.debug(String.format("|%#x|", 93)); // prints: 0x5d, 93
        // with leading "0X" uppercase
        log.debug(String.format("|%#X|", 93)); // prints: 0X5D, 93
        // fixed width prepend '0' with leading "0x"
        log.debug(String.format("%#08x", 93)); // prints: 0x00005d, 93
        // fixed width prepend '0' without leading "0x"
        log.debug(String.format("%08x", 93)); // prints: 00005d, 93

        // Prints the whole string
        log.debug(String.format("|%s|", "Hello"));
        // Specify Field Length
        log.debug(String.format("|%10s|", "Hello"));
        // Left Justify Text
        log.debug(String.format("|%-10s|", "Hello"));
        // Auto expend field length if too long. Prepend with space ' ' if string is shorter.
        log.debug(String.format("|%2s|", "Hello"));
        // Specify Maximum Number of Characters
        log.debug(String.format("|%.2s|", "Hello"));
        // Field Width and Maximum Number of Characters
        log.debug(String.format("|%4.2s|", "Hello"));

        log.debug(String.format("|%tc|", System.currentTimeMillis()));
        log.debug(String.format("|%Tc|", System.currentTimeMillis()));
        log.debug(String.format("|%td|", System.currentTimeMillis()));
        log.debug(String.format("|%tr|", System.currentTimeMillis()));
        log.debug(String.format("|%Tr - %TZ|", System.currentTimeMillis(), System.currentTimeMillis()));
    }
}
