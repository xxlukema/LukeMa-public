package com.learn.test;


import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class IntAndBytesConversionTest {

    @Test
    public void testConvert() {

        int num = 2022;

        byte[] bytes = intToBytes(num);
        log.debug("bytes: {}, len: {}", bytesToHex(bytes), bytes.length);

        int value = bytesToInt(bytes);
        log.debug("int: {}", value);

        bytes = intToBytes2(num);
        log.debug("bytes: {}, len: {}", bytesToHex(bytes), bytes.length);

        value = bytesToInt2(bytes);
        log.debug("int: {}", value);

    }

    // method 1
    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    // method 1, int need 4 bytes, default ByteOrder.BIG_ENDIAN
    public static byte[] intToBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    // method 2, bitwise again, 0xff for sign extension
    public static int bytesToInt2(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF) << 0);
    }

    // method 2, bitwise right shift
    public static byte[] intToBytes2(int value) {
        return new byte[] {
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value };
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            result.append(String.format("%02x", temp));
        }
        return result.toString();
    }
}
