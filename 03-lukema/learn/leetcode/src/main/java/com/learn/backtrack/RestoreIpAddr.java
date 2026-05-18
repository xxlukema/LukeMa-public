package com.learn.backtrack;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC 93
 */
@Log4j2
public class RestoreIpAddr {

    public static void main(String[] args) {

        // String s = "25525511135";
        String s = "101023";

        RestoreIpAddr restoreIpAddr = new RestoreIpAddr();

        List<String> ret = restoreIpAddr.restoreIpAddressesLuke(s);
        log.debug("Restore Ip Addr: {}", () -> ret);

    }

    /**
     * Luke backtrack
     * 
     * Runtime: 25 ms, faster than 5.60% of Java online submissions for Restore IP Addresses.
     * Memory Usage: 45.7 MB, less than 6.63% of Java online submissions for Restore IP Addresses.
     * 
     * Time: O(3 * 4 + 3 ^ 4) --- LEN: s.length()
     * Space: O(LEN)
     */
    public List<String> restoreIpAddressesLuke(final String s) {
        final int LEN = s.length();
        final List<String> listIp = new ArrayList<>();

        /**
         * Space: O(LEN)
         */
        final LinkedList<Integer> ip = new LinkedList<>();

        if (LEN > 12 || LEN < 4) {
            return listIp;
        }

        backtrackLuke(s, 0, listIp, ip);

        return listIp;
    }

    boolean backtrackLuke(final String s, int idx, final List<String> listIp, final LinkedList<Integer> ip) {
        final int LEN = s.length();

        if (idx > LEN - 1) {
            return false;
        }

        char ch = s.charAt(idx);
        List<Integer> options = new ArrayList<>();
        options.add(Integer.valueOf(String.valueOf(ch)));

        /**
         * Time: O(3 * 4)
         */
        if (ch != '0') {
            if (idx + 1 < LEN) {
                char next = s.charAt(idx + 1);
                int val = Integer.valueOf(String.valueOf(ch) + String.valueOf(next));
                options.add(val);

                if (idx + 2 < LEN) {
                    char nextNext = s.charAt(idx + 2);
                    int val2 = Integer.valueOf(String.valueOf(ch) + String.valueOf(next) + String.valueOf(nextNext));
                    if (val2 < 256) {
                        options.add(val2);
                    }
                }
            }
        }

        // log.debug("options: {}, ip: {}", options, ip);

        /**
         * Time: O(3 ^ 4)
         */
        options.forEach(num -> {
            ip.add(num);

            if (ip.size() >= 4) {
                String str = ip.stream().map(String::valueOf).collect(Collectors.joining("."));
                if (str.length() - 3 == LEN) {
                    listIp.add(str);
                    ip.removeLast();
                } else {
                    ip.removeLast();
                }
            } else {
                int nextIdx = idx + String.valueOf(num).length();
                backtrackLuke(s, nextIdx, listIp, ip);
                ip.removeLast();
            }
        });

        return false;
    }

}
