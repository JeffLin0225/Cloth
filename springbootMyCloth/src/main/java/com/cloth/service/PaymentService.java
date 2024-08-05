package com.cloth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final String HASH_KEY = "pwFHCqoQZGmho4w6"; // Same as hashKey in Vue.js
    private static final String HASH_IV = "EkRm7iFT261dpevs"; // Same as hashIV in Vue.js

    public String sendRequestToECPay(Map<String, String> formParams) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Calculate CheckMacValue
        String checkMacValue = generateCheckMacValue(formParams);

        // Add CheckMacValue to formParams
        formParams.put("CheckMacValue", checkMacValue);

        // Convert formParams to MultiValueMap
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        formParams.forEach(body::add);

        // Convert MultiValueMap to form data string for logging
        String requestBodyString = body.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream()
                .map(value -> entry.getKey() + "=" + value))
            .collect(Collectors.joining("&"));

        // Print request details for debugging
        System.out.println("Request URL: https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5");
        System.out.println("Request Body (Form Data): " + requestBodyString);

        // Create HttpEntity with form data
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5",
                HttpMethod.POST,
                request,
                String.class
        );

        // Print response for debugging
        System.out.println("Response Body: " + response.getBody());

        return response.getBody();
    }

    private String generateCheckMacValue(Map<String, String> params) {
        // Sort the parameters by key
        Map<String, String> sortedParams = new TreeMap<>(params);

        // Convert map to formatted string
        String paramString = sortedParams.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));

        // Prepare the data with HashKey and HashIV
        String data = HASH_KEY + "&" + paramString + "&" + HASH_IV;

        // Generate MD5 hash
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error generating CheckMacValue", e);
        }
    }

    private String urlEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8")
            .replace("+", "%20")
            .replace("*", "%2A")
            .replace("%7E", "~");
    }
}