package com.cloth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentService2 {

    private static final String HASH_KEY = "pwFHCqoQZGmho4w6";
    private static final String HASH_IV = "EkRm7iFT261dpevs";

    public String generateCheckMacValue(Map<String, String> formParams) {
        // Convert the formParams to a sorted query string
        String step0 = formParams.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));

        // Sort the query string parameters and create step1
        String step1 = Arrays.stream(step0.split("&"))
            .sorted()
            .collect(Collectors.joining("&"));

        // Create step2 string
        String step2 = "HashKey=" + HASH_KEY + "&" + step1 + "&HashIV=" + HASH_IV;

        // URL encode step2 and convert to upper case
        String step3 = dotNetUrlEncode(java.net.URLEncoder.encode(step2, StandardCharsets.UTF_8));
        String step4 = step3.toUpperCase();

        // Generate SHA256 hash
        return sha256Hex(step4);
    }

    private String sha256Hex(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private String dotNetUrlEncode(String string) {
        // Define replacements for .NET style URL encoding
        Map<String, String> replacements = Map.of(
            "%2D", "-",
            "%5F", "_",
            "%2E", ".",
            "%21", "!",
            "%2A", "*",
            "%28", "(",
            "%29", ")",
            "%20", "+"
        );

        // Apply replacements
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue());
        }
        return string;
    }
}
