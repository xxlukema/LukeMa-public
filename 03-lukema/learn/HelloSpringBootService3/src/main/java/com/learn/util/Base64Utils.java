package com.learn.util;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Utils {

    public static String decodeUrlsafeInput(String urlsafeStr) {
        if (urlsafeStr == null) {
            return null;
        }

        byte[] decodedBytes = Base64.getUrlDecoder().decode(urlsafeStr);
        return new String(decodedBytes);
    }

    public static String decodeUrlsafeInput(String urlsafeStr, Charset charSet) {
        if (urlsafeStr == null) {
            return null;
        }

        byte[] decodedBytes = Base64.getUrlDecoder().decode(urlsafeStr);
        return new String(decodedBytes, charSet);
    }

    public static String decodeUrlsafeInputUtf8(String urlsafeStrUtf8, Charset charSet) {
        return decodeUrlsafeInput(urlsafeStrUtf8, StandardCharsets.UTF_8);
    }

}
