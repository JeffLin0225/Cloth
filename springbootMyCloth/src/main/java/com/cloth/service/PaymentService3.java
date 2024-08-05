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

import java.util.Map;

@Service
public class PaymentService3 {

    public String sendRequestToECPay(Map<String, String> formParams) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 將 formParams 轉換為 MultiValueMap
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        formParams.forEach(body::add);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5",
                HttpMethod.POST,
                request,
                String.class
        );

        return response.getBody();
    }
}
