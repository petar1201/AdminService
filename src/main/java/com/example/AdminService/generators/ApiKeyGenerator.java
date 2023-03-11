package com.example.AdminService.generators;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ApiKeyGenerator {
    public static String generateApiKey() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            byte[] apiKeyBytes = new byte[32]; // 32 bytes = 256 bits
            random.nextBytes(apiKeyBytes);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedBytes = digest.digest(apiKeyBytes);
            return bytesToHex(encodedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not generate API key", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
