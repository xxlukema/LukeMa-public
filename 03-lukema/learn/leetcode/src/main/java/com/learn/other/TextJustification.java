package com.learn.other;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class TextJustification {

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        String[] words0 = { "This", "is", "an", "example", "of", "text", "justification." };
        int maxWidth0 = 16;

        String[] words1 = { "What", "must", "be", "acknowledgment", "shall", "be" };
        int maxWidth1 = 16;

        String[] words2 = {
                "Science", "is", "what", "we", "understand", "well", "enough", "to", "explain",
                "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do" };
        int maxWidth2 = 20;

        TextJustification textJustification = new TextJustification();
        List<String> ret = textJustification.fullJustify(words0, maxWidth0);

        var str = ret.stream().collect(Collectors.joining("\n"));
        log.debug("text Luke: \n{}", () -> str);

    }

    /**
     * Runtime: 2 ms, faster than 39.27% of Java online submissions for Text Justification.
     * Memory Usage: 42.9 MB, less than 13.85% of Java online submissions for Text Justification.
     *
     * Time: O(n)
     * Space: O(sum(n * word.length()))
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        LinkedList<String> line = new LinkedList<>();
        List<String> adjustedLines = new ArrayList<>();
        int len = 0;
        int i = 0;
        while (i < words.length) {
            if (len + words[i].length() + line.size() > maxWidth) {
                adjustLine(line, len, maxWidth, adjustedLines);

                len = 0;
                line = new LinkedList<>();
            }

            line.add(words[i]);
            len += words[i].length();
            i++;
        }

        /**
         * Remaining as the last line
         */
        if (len != 0) {
            StringBuilder sb = new StringBuilder();
            String str = line.stream().collect(Collectors.joining(" "));
            sb.append(str);
            while (sb.length() < maxWidth) {
                sb.append(' ');
            }
            adjustedLines.add(sb.toString());
        }

        return adjustedLines;
    }

    private void adjustLine(LinkedList<String> line, int len, int maxWidth, List<String> adjustedLines) {

        int spaces = (maxWidth - len);

        StringBuilder sb = new StringBuilder();

        if (line.size() == 1) {
            sb.append(line.removeFirst());
            int count = 0;
            while (count++ < spaces) {
                sb.append(' ');
            }
            adjustedLines.add(sb.toString());
        } else {
            int extraSpaces = spaces - (line.size() - 1);
            int minSpace = extraSpaces / (line.size() - 1);
            int remain = extraSpaces % (line.size() - 1);
            int remainCounter = 0;

            while (line.size() > 1) {
                sb.append(line.removeFirst()).append(' ');
                int count = 0;
                while (count++ < minSpace) {
                    sb.append(' ');
                }
                if (remainCounter++ < remain) {
                    sb.append(' ');
                }
            }
            /**
             * Append last word of the line
             */
            sb.append(line.removeFirst());

            adjustedLines.add(sb.toString());
        }
    }
}
