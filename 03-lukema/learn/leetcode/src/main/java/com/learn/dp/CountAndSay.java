package com.learn.dp;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CountAndSay {

    public static void main(String[] args) {
        CountAndSay countAndSay = new CountAndSay();
        String str = countAndSay.countAndSay(10);

        log.info("{}", () -> str);
    }

    public String countAndSay(int n) {

        List<String> memo = new ArrayList<>();

        return countAndSay(n, memo);
    }

    public String countAndSay(int n, List<String> memo) {

        String str = null;

        if (n < memo.size()) {
            str = memo.get(n);
        }

        if (str != null) {

            log.debug("hit {}", () -> n);

            return str;
        } else {
            log.debug("not hit {}", () -> n);
        }

        if (n == 1) {
            str = "1";
            memo.add(str);
            return str;
        }

        String s = countAndSay(n - 1, memo);
        StringBuffer sb = new StringBuffer();

        int pos = 0;
        while (pos < s.length()) {
            char ch = s.charAt(pos);
            int next = pos + 1;
            while (next < s.length()) {
                char nextCh = s.charAt(next);
                if (ch == nextCh) {
                    next++;
                } else {
                    break;
                }
            }
            sb.append(next - pos).append(ch);
            pos = next;
        }

        str = sb.toString();

        memo.add(str);

        return str;
    }
}
