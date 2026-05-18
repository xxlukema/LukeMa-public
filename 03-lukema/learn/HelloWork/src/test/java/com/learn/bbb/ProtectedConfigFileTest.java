package com.learn.bbb;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class ProtectedConfigFileTest {

    private static final Logger LOG = LogManager.getLogger();

    private final char[] PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
    private final byte[] SALT = { (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, };

    //private final byte[] SALT = "Hello World!".getBytes();

    private final boolean UrlSafe = true;
    private final int interationCount = 1000 * 1000 * 1;
    private final String Encoding = "UTF-8";
    private final String CipherAlgorithmNames = "PBEWithMD5AndDES"; // "PBEWithHmacSHA1AndDESede";

    @Test
    public void test()
        throws Exception {
        String originalPassword = "secret";
        LOG.info("Original password: " + originalPassword);
        String encryptedPassword = encrypt(originalPassword);

        encryptedPassword = encryptedPassword.trim();

        LOG.info("Encrypted password: " + encryptedPassword);
        String decryptedPassword = decrypt(encryptedPassword);
        LOG.info("Decrypted password: " + decryptedPassword);
    }

    private String encrypt(String property)
        throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CipherAlgorithmNames);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance(CipherAlgorithmNames);
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, interationCount));
        return base64Encode(pbeCipher.doFinal(property.getBytes(Encoding)));
    }

    private String base64Encode(byte[] bytes) {
        return new Base64(UrlSafe).encodeToString(bytes);
    }

    private String decrypt(String property)
        throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CipherAlgorithmNames);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, interationCount));
        return new String(pbeCipher.doFinal(base64Decode(property)), Encoding);
    }

    private byte[] base64Decode(String property)
        throws IOException {
        return new Base64(UrlSafe).decode(property);
    }

}
