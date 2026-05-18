package com.freddiemac.jwt.encode;


import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;


/**
 * This is to log when encoding happens.
 * 
 * admin -> My-Encoded-admin.
 * user -> My-Encoded-user.
 */
@Log4j2
public class MyPasswordEnconder
    implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {

        String encodedString = doEncode(charSequence);

        log.debug("encoding password: {}", () -> encodedString);

        return encodedString;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {

        String encodedString = doEncode(charSequence);

        log.debug(" === Auth matching passwords: '{}' with '{}'", () -> encodedString, () -> s);

        return encodedString.equals(s);
    }

    private String doEncode(CharSequence charSequence) {
        if (charSequence == null) {
            return "null";
        } else {
            return "My-Encoded-" + charSequence.toString() + ".";
        }
    }

}
