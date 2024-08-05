package com.cloth.Controller;


import com.cloth.model.Cart;
import com.cloth.model.Coupon;
import com.cloth.model.Couponowner;
import com.cloth.model.Orderdetail;
import com.cloth.model.Orders;
import com.cloth.model.Product;
import com.cloth.model.Users;
import com.cloth.Repository.CouponRepository;
import com.cloth.Repository.OrderDetailRepository;
import com.cloth.Repository.UsersRepository;
import com.cloth.service.CouponService;
import com.cloth.service.OrderService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
	
   @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private CouponService couponService;
    
    @Autowired
    private CouponRepository couponownerRepository;
   
//用單號找訂單資訊
    @GetMapping("/detail/{Id}")
    public ResponseEntity<String> getOrderById(@PathVariable Integer Id) {
    	List<Orders> orders = orderService.findOrderById(Id);
    	
    	// JSON 響應
        JSONArray responseArray = new JSONArray();
        for (Orders order : orders) {
            JSONObject orderJson = new JSONObject();
            orderJson.put("id", order.getId());
            orderJson.put("orderDate", order.getCreated_at());
            orderJson.put("status", order.getStatus());
            orderJson.put("totalAmount", order.getTotalAmount());
            orderJson.put("recipientName", order.getShipname());
            orderJson.put("recipientPhone", order.getShipphone());
            orderJson.put("shippingMethod", order.getShipment());
            orderJson.put("address", order.getShipaddress());
            orderJson.put("paymentMethod", order.getPayment());
            orderJson.put("ordernum", order.getOrdernum());
            

            // 訂單詳細信息
            JSONArray orderDetailsArray = new JSONArray();
            for (Orderdetail orderDetail : order.getOrderdetail()) {
                JSONObject orderDetailJson = new JSONObject();
                orderDetailJson.put("id", orderDetail.getId());
                orderDetailJson.put("cart_id", orderDetail.getCart());
                orderDetailJson.put("order_id", orderDetail.getOrders());
                orderDetailJson.put("return_id", orderDetail.getReturns());


                // 商品信息
                Product product = orderDetail.getCart().getProduct();
                JSONObject productJson = new JSONObject();
                productJson.put("id", product.getId());
                productJson.put("name", product.getName());
                productJson.put("description", product.getDescription());
                productJson.put("activity", product.getActivity().getName());
                productJson.put("quantity", product.getQuantity());
                productJson.put("size", product.getSize());
                productJson.put("price", product.getPrice());
                productJson.put("discount_price", product.getDiscount_price());
                productJson.put("img", product.getImg());
                productJson.put("img2", product.getImg2());
                productJson.put("img3", product.getImg3());
                productJson.put("img4", product.getImg4());
                productJson.put("status", product.getStatus());
                productJson.put("color", product.getColor());
                productJson.put("type", product.getType());
                productJson.put("gender", product.getGender());
                productJson.put("style", product.getStyle());

                orderDetailJson.put("product", productJson);
                orderDetailsArray.put(orderDetailJson);
            }
            orderJson.put("orderDetails", orderDetailsArray);

            responseArray.put(orderJson);
        }

        return ResponseEntity.ok(responseArray.toString());
    }
   
    
 // 用會員id找購物車
    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getOrdersByUserId(@PathVariable Integer userId) {
        List<Orders> orders = orderService.findOrdersByUserId(userId);

        // JSON 響應
        JSONArray responseArray = new JSONArray();
        for (Orders order : orders) {
            JSONObject orderJson = new JSONObject();
            orderJson.put("id", order.getId());
            orderJson.put("orderDate", order.getCreated_at());
            orderJson.put("status", order.getStatus());
            orderJson.put("totalAmount", order.getTotalAmount());
            orderJson.put("recipientName", order.getShipname());
            orderJson.put("recipientPhone", order.getShipphone());
            orderJson.put("shippingMethod", order.getShipment());
            orderJson.put("address", order.getShipaddress());
            orderJson.put("paymentMethod", order.getPayment());
            orderJson.put("ordernum", order.getOrdernum());
            

            // 訂單詳細信息
            JSONArray orderDetailsArray = new JSONArray();
            for (Orderdetail orderDetail : order.getOrderdetail()) {
                JSONObject orderDetailJson = new JSONObject();
                orderDetailJson.put("id", orderDetail.getId());
                orderDetailJson.put("cart_id", orderDetail.getCart());
                orderDetailJson.put("order_id", orderDetail.getOrders());
                orderDetailJson.put("return_id", orderDetail.getReturns());


                // 商品信息
                Product product = orderDetail.getCart().getProduct();
                JSONObject productJson = new JSONObject();
                productJson.put("id", product.getId());
                productJson.put("name", product.getName());
                productJson.put("description", product.getDescription());
                productJson.put("activity", product.getActivity().getName());
                productJson.put("quantity", product.getQuantity());
                productJson.put("size", product.getSize());
                productJson.put("price", product.getPrice());
                productJson.put("discount_price", product.getDiscount_price());
                productJson.put("img", product.getImg());
                productJson.put("img2", product.getImg2());
                productJson.put("img3", product.getImg3());
                productJson.put("img4", product.getImg4());
                productJson.put("status", product.getStatus());
                productJson.put("color", product.getColor());
                productJson.put("type", product.getType());
                productJson.put("gender", product.getGender());
                productJson.put("style", product.getStyle());

                orderDetailJson.put("product", productJson);
                orderDetailsArray.put(orderDetailJson);
            }
            orderJson.put("orderDetails", orderDetailsArray);

            responseArray.put(orderJson);
        }

        return ResponseEntity.ok(responseArray.toString());
    }

    
    //用會員id找擁有的優惠券
    @GetMapping("/coupons/{userId}")
    public ResponseEntity<String> getCouponsByUserId(@PathVariable Integer userId) {
        // Logging the userId for debugging purposes
        System.out.println("Fetching coupons for userId: " + userId);

        List<Couponowner> couponOwners = couponService.getActiveCouponOwnersByUserId(userId);
        JSONArray couponArray = new JSONArray();

        for (Couponowner couponOwner : couponOwners) {
            System.out.println("Processing couponOwner: " + couponOwner);
            Coupon coupon = couponOwner.getCoupon();
            JSONObject ownerJson = new JSONObject();
            JSONObject couponJson = new JSONObject();
            
            ownerJson.put("owner_Id", couponOwner.getId());

            couponJson.put("id", coupon.getId());
            couponJson.put("name", coupon.getName());
            couponJson.put("description", coupon.getDescription());
            couponJson.put("discount", coupon.getDiscount());
            couponJson.put("quantity", coupon.getQuantity());
            couponJson.put("start_at", coupon.getStart_at());  // Assuming getStartAt() returns a formatted date string
            couponJson.put("end_at", coupon.getEnd_at());     // Assuming getEndAt() returns a formatted date string

            JSONObject combinedJson = new JSONObject();
            combinedJson.put("owner", ownerJson);
            combinedJson.put("coupon", couponJson);

            couponArray.put(combinedJson);
        }

        JSONObject response = new JSONObject();
        response.put("coupons", couponArray);

        // Logging the final response for debugging purposes
        System.out.println("Response: " + response.toString());

        return ResponseEntity.ok(response.toString());
    }
    
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        @SuppressWarnings("unchecked")
        List<Integer> cartItemIds = (List<Integer>) request.get("cartItemIds");
        String ship_address = (String) request.get("ship_address");
        String ship_name = (String) request.get("ship_name");
        String shipPhone = (String) request.get("shipPhone");
        String shipping = (String) request.get("shipping");
        String paymentMethod = (String) request.get("paymentMethod");

        // 处理 totalAmount 转换
        Integer totalAmount;
        try {
            totalAmount = Integer.parseInt((String) request.get("totalAmount"));
        } catch (NumberFormatException e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Invalid total amount format");
            return ResponseEntity.badRequest().body(errorResponse.toString());
        }

        // 打印请求参数
        System.out.println("Received checkout request with parameters:");
        System.out.println("userId: " + userId);
        System.out.println("cartItemIds: " + cartItemIds);
        System.out.println("ship_address: " + ship_address);
        System.out.println("ship_name: " + ship_name);
        System.out.println("shipPhone: " + shipPhone);
        System.out.println("shipping: " + shipping);
        System.out.println("paymentMethod: " + paymentMethod);
        System.out.println("totalAmount: " + totalAmount);

        Users user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Invalid user ID");
            return ResponseEntity.badRequest().body(errorResponse.toString());
        }

        List<Cart> cartItems = orderService.getCartItemsForUser(userId);
        List<Cart> selectedCartItems = cartItems.stream()
            .filter(cart -> cartItemIds.contains(cart.getId()))
            .collect(Collectors.toList());

        if (selectedCartItems.isEmpty()) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "No items selected for checkout");
            return ResponseEntity.badRequest().body(errorResponse.toString());
        }

        // 更新选中商品的状态
        selectedCartItems.forEach(cart -> cart.setStatus(1)); // 假设1表示已结账
        orderService.updateCartItems(selectedCartItems); // 保存更新到数据库

        Orders order = orderService.createOrder(user, selectedCartItems, ship_address, ship_name, shipPhone, shipping, totalAmount);

        // JSON 响应
        JSONObject responseJson = new JSONObject();
        responseJson.put("orderId", order.getId());

        JSONArray cartItemsJson = new JSONArray();
        for (Cart cart : selectedCartItems) {
            JSONObject cartItemJson = new JSONObject();
            cartItemJson.put("cartId", cart.getId());
            cartItemJson.put("productId", cart.getProduct().getId());
            cartItemJson.put("quantity", cart.getQuantity());
            cartItemsJson.put(cartItemJson);
        }
        responseJson.put("cartItems", cartItemsJson);

        // 根据支付方式处理
        if ("貨到付款".equals(paymentMethod)) {
            return ResponseEntity.ok(responseJson.toString());
        } else {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Invalid payment method");
            return ResponseEntity.badRequest().body(errorResponse.toString());
        }
    }
      
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody String orderRequestStr) {
        try {
            orderService.createOrder(orderRequestStr);
            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    
    
    public static class CheckoutRequest {
        private List<Integer> cartItemIds;
        private String paymentMethod;
        private double totalAmount;

        public List<Integer> getCartItemIds() {
            return cartItemIds;
        }

        public void setCartItemIds(List<Integer> cartItemIds) {
            this.cartItemIds = cartItemIds;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
  
//    @PostMapping
//    public ResponseEntity<Orders> createOrder(@RequestBody Map<String, Object> orderData) {
//        Orders order = new Orders();
//
//        // 设置订单用户信息
//        Integer userId = (Integer) orderData.get("userId");
//        Users user = new Users();
//        user.setId(userId);
//        order.setUsers(user);
//
//        // 设置折扣券信息
//        Map<String, Object> selectedCoupon = (Map<String, Object>) orderData.get("selectedCoupon");
//        if (selectedCoupon != null) {
//            Map<String, Object> owner = (Map<String, Object>) selectedCoupon.get("owner");
//            if (owner != null) {
//                Integer ownerId = (Integer) owner.get("owner_Id");
//                Couponowner couponOwner = new Couponowner();
//                couponOwner.setId(ownerId);
//                order.setCouponowner(couponOwner);
//            }
//        }
//
//        // 设置其他订单信息
//        order.setShip_address((String) orderData.get("address"));
//        order.setShipping((String) orderData.get("shippingMethod"));
//        order.setPay((String) orderData.get("paymentMethod"));
//
//        // 安全转换 totalAmount
//        Object totalAmountObj = orderData.get("totalAmount");
//        if (totalAmountObj instanceof Number) {
//            order.setTotalAmount(((Number) totalAmountObj).intValue());
//        } else if (totalAmountObj instanceof String) {
//            try {
//                order.setTotalAmount(Integer.parseInt((String) totalAmountObj));
//            } catch (NumberFormatException e) {
//                return ResponseEntity.badRequest().body(null);
//            }
//        } else {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        // 设置订单详细信息
//        List<Map<String, Object>> selectedItems = (List<Map<String, Object>>) orderData.get("selectedItems");
//        for (Map<String, Object> itemData : selectedItems) {
//            Orderdetail orderDetail = new Orderdetail();
//
//            // 安全转换 productId
//            Object productIdObj = itemData.get("productId");
//            if (productIdObj instanceof Number) {
//                orderDetail.setProductId(((Number) productIdObj).intValue());
//            } else if (productIdObj instanceof String) {
//                try {
//                    orderDetail.setProductId(Integer.parseInt((String) productIdObj));
//                } catch (NumberFormatException e) {
//                    return ResponseEntity.badRequest().body(null);
//                }
//            } else {
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            // 安全转换 quantity
//            Object quantityObj = itemData.get("quantity");
//            if (quantityObj instanceof Number) {
//                orderDetail.setQuantity(((Number) quantityObj).intValue());
//            } else if (quantityObj instanceof String) {
//                try {
//                    orderDetail.setQuantity(Integer.parseInt((String) quantityObj));
//                } catch (NumberFormatException e) {
//                    return ResponseEntity.badRequest().body(null);
//                }
//            } else {
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            // 安全转换 price
//            Object priceObj = itemData.get("price");
//            if (priceObj instanceof Number) {
//                orderDetail.setPrice(((Number) priceObj).doubleValue());
//            } else if (priceObj instanceof String) {
//                try {
//                    orderDetail.setPrice(Double.parseDouble((String) priceObj));
//                } catch (NumberFormatException e) {
//                    return ResponseEntity.badRequest().body(null);
//                }
//            } else {
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            // 安全转换 discountPrice
//            Object discountPriceObj = itemData.get("discountPrice");
//            if (discountPriceObj instanceof Number) {
//                orderDetail.setDiscountPrice(((Number) discountPriceObj).doubleValue());
//            } else if (discountPriceObj instanceof String) {
//                try {
//                    orderDetail.setDiscountPrice(Double.parseDouble((String) discountPriceObj));
//                } catch (NumberFormatException e) {
//                    return ResponseEntity.badRequest().body(null);
//                }
//            } else {
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            order.getOrderdetail().add(orderDetail);
//        }
//
//        // 设置订单状态和时间
//        order.setStatus("Pending");
//        order.setCreated_at(new Date());
//        order.setUpdated_at(new Date());
//
//        // 保存订单
//        Orders savedOrder = orderService.saveOrder(order);
//
//        return ResponseEntity.ok(savedOrder);
//    }
    
    

    
}
