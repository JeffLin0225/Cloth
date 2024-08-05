package com.cloth.Controller;

import com.cloth.model.Couponowner;
import com.cloth.service.CouponownerService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/couponowner")
@CrossOrigin
public class CouponownerController {
	
    @Autowired
    private final CouponownerService couponownerService;


    public CouponownerController(CouponownerService couponownerService) {
        this.couponownerService = couponownerService;
    }

//    @GetMapping("/user/coupons/{userId}")
//    public ResponseEntity<String> getCouponsByUserId(@PathVariable Integer userId) {
//        List<Couponowner> coupons = couponownerService.findCouponsByUserId(userId);
//
//        JSONArray responseArray = new JSONArray();
//        for (Couponowner couponowner : coupons) {
//            JSONObject couponJson = new JSONObject();
//            couponJson.put("id", couponowner.getId());
//            couponJson.put("status", couponowner.getStatus());
//            couponJson.put("start_at", couponowner.getStart_at());
//            couponJson.put("end_at", couponowner.getEnd_at());
//
//            couponJson.put("coupon", couponJson);
//
//            responseArray.put(couponJson);
//        }
//
//        return ResponseEntity.ok(responseArray.toString());
//    }

    @PostMapping("/user/{userId}/addCoupon")
    public String addCouponForUser(@PathVariable Integer userId, @RequestBody String jsonBody) {
        JSONObject jsonObject = new JSONObject(jsonBody);
        // 根據前端發送的 JSON 數據進行相應處理，例如解析後添加到用戶的優惠券列表中
        // 這裡只是示例，具體邏輯需要根據你的需求來實現
        String couponCode = jsonObject.getString("couponCode");
        // 其他處理邏輯...

        return "Coupon added successfully";
    }
    
    //更改coupon status 
    @PutMapping("/status")
    public ResponseEntity<Void> updateCouponStatus(@RequestBody List<Integer> ids) {
        try {
            couponownerService.updateCouponStatusToZero(ids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Error handling
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @PutMapping("/status")
//    public void updateCouponStatus(@RequestBody List<Integer> ids) {
//    	couponownerService.updateCouponStatusToZero(ids);
//    }
}

