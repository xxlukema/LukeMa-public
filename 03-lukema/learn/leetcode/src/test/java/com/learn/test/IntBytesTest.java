package com.learn.test;


import java.nio.ByteBuffer;
import java.util.HexFormat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class IntBytesTest {

    @Test
    public void testIntBytes() {

        // int in = 2023;
        // int in = 0x4A8B7C6D;
        // int in = 1250655341;
        int in = 0;

        byte[] myBytes = intToBytes2(in);

        byte[] bufferBytes = intToBytes(in);

        log.debug("bytes as String: {}, len: {}", String.valueOf(myBytes), String.valueOf(myBytes).length());

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < Integer.BYTES; k++) {
            sb.append((char) myBytes[k]);
        }

        log.debug("sb: {}, len: {}", sb.toString(), sb.length());

        String hex = String.format("%#08x", in);
        log.debug("in hex: {}, len: {}", hex, hex.length());

        int hexToint = HexFormat.fromHexDigits(hex.substring(2));
        log.debug(hexToint);

        Assertions.assertEquals(in, hexToint);

        /*
        log.debug("bytes: {}, len: {}", bytes, bytes.length);

        log.debug("i: {}", String.format("%d", i));
        log.debug("i: {}", String.format("%#X", i));
        log.debug("max: {}", String.format("%#X", Integer.MIN_VALUE));

        */
        for (int k = 0; k < bufferBytes.length; k++) {
            // log.debug("bytes: {}", String.format("%#X", bytes[k]));

            if (bufferBytes[k] != myBytes[k]) {
                log.debug("bytes: {}, bytes2: {}", String.format("%#X", bufferBytes[k]), String.format("%#X", myBytes[k]));
            }
        }

        in = bytesToInt2(bufferBytes);

        int ii = bytesToInt(myBytes);
        log.debug("ii: {}", ii);

        Assertions.assertEquals(in, ii);

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
}
