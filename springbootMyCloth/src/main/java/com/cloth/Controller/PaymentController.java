package com.cloth.Controller;

import com.cloth.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/submitPayment")
    public String submitPayment(@RequestParam Map<String, String> formParams) {
        // 打印接收到的参数
        formParams.forEach((key, value) -> System.out.println(key + ": " + value));

        // 将表单参数转发到 ECPay
        String response = paymentService.sendRequestToECPay(formParams);

        // 返回响应给前端
        return response;
    }
}