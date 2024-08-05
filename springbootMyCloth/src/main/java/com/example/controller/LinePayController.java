package com.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LinePayRequest;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/linepay")
@CrossOrigin(origins = "http://localhost:5173")
public class LinePayController {
    @PostMapping("/request")
    public ResponseEntity<?> createLinePayRequest(@RequestBody LinePayRequest request) {
        // Process request
        return ResponseEntity.ok("Request received");
    }
}


