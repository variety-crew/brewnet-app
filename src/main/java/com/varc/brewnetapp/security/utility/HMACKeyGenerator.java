package com.varc.brewnetapp.security.utility;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class HMACKeyGenerator {
    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            keyGen.init(512);

            SecretKey secretKey = keyGen.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            log.info("HS512 Key: {}", encodedKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
