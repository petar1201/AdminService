package com.example.AdminService.generators;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32]; // 32 bytes = 256 bits
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}