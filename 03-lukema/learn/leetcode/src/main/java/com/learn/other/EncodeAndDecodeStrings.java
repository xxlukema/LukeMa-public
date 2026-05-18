package com.learn.other;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;


/**
 * LC-271 Encode And Decode Strings
 *
 * Medium
 *
 * Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and
 * is decoded back to the original list of strings.
 *
 * Machine 1 (sender) has the function:
 *
 * string encode(vector<string> strs) {
 *   // ... your code
 *   return encoded_string;
 * }
 *
 * Machine 2 (receiver) has the function:
 * vector<string> decode(string s) {
 *   //... your code
 *   return strs;
 * }
 *
 * So Machine 1 does:
 * string encoded_string = encode(strs);
 *
 * and Machine 2 does:
 * vector<string> strs2 = decode(encoded_string);
 *
 * strs2 in Machine 2 should be the same as strs in Machine 1.
 *
 * Implement the encode and decode methods.
 *
 * You are not allowed to solve the problem using any serialize methods (such as eval).
 *
 * Example 1:
 * Input: dummy_input = ["Hello","World"]
 * Output: ["Hello","World"]
 * Explanation:
 * Machine 1:
 * Codec encoder = new Codec();
 * String msg = encoder.encode(strs);
 * Machine 1 ---msg---> Machine 2
 *
 * Machine 2:
 * Codec decoder = new Codec();
 * String[] strs = decoder.decode(msg);
 *
 * Example 2:
 * Input: dummy_input = [""]
 * Output: [""]
 *
 * Constraints:
 *     1 <= strs.length <= 200
 *     0 <= strs[i].length <= 200
 *     strs[i] contains any possible characters out of 256 valid ASCII characters.
 *
 * Follow up: Could you write a generalized algorithm to work on any possible set of characters?
 */
@Log4j2
public class EncodeAndDecodeStrings {

    public static void main(String[] args) {

        List<String> list = List.of("Hello", "World");

        Codec codec = new Codec();

        String encodeLukeNaive = codec.encodeLukeNaive(list);
        log.debug("encoded: {}", encodeLukeNaive);

        var ret = codec.decodeLukeNaive(encodeLukeNaive);
        log.debug("decoded: {}", ret);

        var encodeLuke = codec.encodeLuke(list);
        log.debug("encoded: {}", encodeLuke);

        ret = codec.decodeLuke(encodeLuke);
        log.debug("decoded: {}", ret);

        var encode = codec.encode(list);
        log.debug("encoded: {}", encode);

        ret = codec.decode(encode);
        log.debug("decoded: {}", ret);

    }
}


/**
 * Trick 1: Use fixed length String to represent next String length.
 * Trick 2: Use byte shift to convert int length into 4 byte array.
 * Trick 3: Convert 4 byte array to 4 char array.
 * Trick 4: Use ByteBuffer to convert int to 4 byte array, and convert 4 byte array back to int.
 * Trick 5: Use 8 char hex string to represent length. This is 4 char extra pay load for length.
 *
 * Runtime: 28 ms Beats 25.32%
 * Memory: 43.7 MB Beats 72.49%
 *
 * Time: O(word length)
 * Space: O(word length)
 */
class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();

        for (String str : strs) {
            int len = str.length();
            byte[] bytes = intToBytes2(len);
            String str4 = bytesTo4charString(bytes);
            sb.append(str4).append(str);
        }

        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> list = new ArrayList<>();

        while (!s.isEmpty()) {
            String lenStr = s.substring(0, 4);
            byte[] bytes = str4charToBytes(lenStr);
            int len = bytesToInt2(bytes);
            String str = s.substring(4, 4 + len);
            list.add(str);
            s = s.substring(4 + len);
        }

        return list;
    }

    public String bytesTo4charString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append((char) bytes[i]);
        }

        return sb.toString();
    }

    public byte[] str4charToBytes(String str) {
        byte[] bytes = new byte[str.length()];
        char[] chs = str.toCharArray();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) chs[i];
        }
        return bytes;
    }

    public byte[] intToBytes(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    public int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public byte[] intToBytes2(int in) {
        byte[] bytes = new byte[Integer.BYTES];

        /**
         * left most byte (the most significant byte) has index 0:
         *
         * k = 0: idx = 3, keeps the least significant bytes
         * k = 3: idx = 0, keeps the most significant bytes
         */
        for (int k = 0; k < Integer.BYTES; k++) {
            bytes[Integer.BYTES - 1 - k] = (byte) ((in >> (k * Byte.SIZE)) & 0xff);
            // log.debug("bytes2: {}", String.format("%#X", bytes[k]));
        }

        return bytes;
    }

    public int bytesToInt2(byte[] bytes) {
        int ret = 0;

        for (int k = 0; k < Integer.BYTES; k++) {
            ret |= (bytes[Integer.BYTES - 1 - k] & 0xff) << (k * Byte.SIZE);
        }

        return ret;
    }

    String delimtLuke = ":";

    String intToString(int len) {
        return String.format("%32s", len);
    }

    // Encodes a list of strings to a single string.
    public String encodeLuke(List<String> strs) {
        StringBuilder sb = new StringBuilder();

        for (String str : strs) {
            int len = str.length();
            sb.append(String.valueOf(len)).append(delimtLuke).append(str);
        }

        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decodeLuke(String s) {
        List<String> list = new ArrayList<>();

        int pos = 0;
        while ((pos = s.indexOf(delimtLuke)) > 0) {
            int len = Integer.valueOf(s.substring(0, pos));
            String str = s.substring(pos + 1, pos + 1 + len);
            list.add(str);
            s = s.substring(pos + 1 + len);
        }

        return list;
    }

    static final String NULL = "NULL";

    // Encodes a list of strings to a single string.
    public String encodeLukeNaive(List<String> strs) {
        if (strs.size() == 0) {
            return "";
        }
        String name = this.getClass().getSimpleName();
        String head = null;
        while (true) {
            String delim = name + System.currentTimeMillis();
            long count = strs.stream().filter(e -> e != null && e.indexOf(delim) != -1).count();
            if (count == 0) {
                head = delim;
                break;
            }
        }
        return head + "_" + strs.stream().map(e -> e == null ? NULL : e).collect(Collectors.joining(head));
    }

    // Decodes a single string to a list of strings.
    public List<String> decodeLukeNaive(String s) {
        if (s.isEmpty()) {
            return new ArrayList<>();
        }

        int idx = s.indexOf("_");

        String delim = s.substring(0, idx);

        String data = s.substring(idx + 1);

        // log.debug("data: {}, delim: {}", data, delim);

        List<String> list = new ArrayList<>();

        /*
        int len = delim.length();

        while (true) {
            int pos = data.indexOf(delim);
            if (pos == -1) {
                list.add(data);
                break;
            } else {
                String f = data.substring(0, pos);
                data = data.substring(pos + len);
                if (f.isEmpty()) {
                    list.add("");
                } else if (s.equals(NULL)) {
                    list.add(null);
                } else {
                    list.add(f);
                }
            }
        }
        */

        String[] fields = data.split(delim);

        for (String f : fields) {
            if (f.isEmpty()) {
                list.add("");
            } else if (s.equals(NULL)) {
                list.add(null);
            } else {
                list.add(f);
            }
        }

        return list;
    }

    /**
     * LC1
     */
    public String encodeLc1(List<String> strs) {
        if (strs.size() == 0) {
            return Character.toString((char) 258);
        }

        String d = Character.toString((char) 257);
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
            sb.append(d);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decodeLc1(String s) {
        String d = Character.toString((char) 258);
        if (s.equals(d)) {
            return new ArrayList<>();
        }

        d = Character.toString((char) 257);
        return Arrays.asList(s.split(d, -1));
    }

    /**
     * LC2
     */
    // Encodes string length to bytes string
    public String intToStringLc2(String s) {
        int len = s.length();
        char[] bytes = new char[4];
        for (int i = 3; i > -1; --i) {
            bytes[3 - i] = (char) (len >> (i * 8) & 0xff);
        }
        return new String(bytes);
    }

    // Encodes a list of strings to a single string.
    public String encodeLc2(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(intToStringLc2(s));
            sb.append(s);
        }
        return sb.toString();
    }

    // Decodes bytes string to integer
    public int stringToIntLc2(String bytesStr) {
        int result = 0;
        for (char b : bytesStr.toCharArray()) {
            result = (result << 8) + (int) b;
        }
        return result;
    }

    // Decodes a single string to a list of strings.
    public List<String> decodeLc2(String s) {
        int i = 0, n = s.length();
        List<String> output = new ArrayList<>();
        while (i < n) {
            int length = stringToIntLc2(s.substring(i, i + 4));
            i += 4;
            output.add(s.substring(i, i + length));
            i += length;
        }
        return output;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));
