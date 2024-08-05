package com.cloth.service;

import com.cloth.model.Cart;
import com.cloth.model.Couponowner;
import com.cloth.model.Orderdetail;
import com.cloth.model.Orders;
import com.cloth.model.Users;
import com.cloth.Repository.CartRepository;
import com.cloth.Repository.CouponownerRepository;
import com.cloth.Repository.OrderDetailRepository;
import com.cloth.Repository.OrdersRepository;
import com.cloth.Repository.UsersRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private OrderDetailRepository orderdetailRepository;
    
    @Autowired
    private CouponownerRepository couponownerRepository;
    @Autowired
    private UsersRepository usersRepository;
    
    @Transactional
    public List<Orders> findOrdersByUserId(Integer userId) {
        return ordersRepository.findOrdersByUserId(userId);
    }
    @Transactional
    public List<Orders> findOrderById(Integer id) {
        return ordersRepository.findOrderById(id);
    }
    
   
    //購物車第三到四步 按下結帳鈕 建立訂單＆訂單明細 改變購物車、折價券狀態
    @Transactional
    public void createOrder(String json) {
        JSONObject obj = new JSONObject(json);
        
        JSONObject orderJson = obj.getJSONObject("order");

        Integer userinteger = orderJson.getInt("userId");
        
        Optional<Users>optional = usersRepository.findById(userinteger);
        Users user =optional.get();
//        Integer userId = orderJson.isNull("userId") ? null : orderJson.getInt("userId");
        String recipientName = orderJson.isNull("recipientName") ? null : orderJson.getString("recipientName");
        String recipientPhone = orderJson.isNull("recipientPhone") ? null : orderJson.getString("recipientPhone");
        String shippingMethod = orderJson.isNull("shippingMethod") ? null : orderJson.getString("shippingMethod");
        String address = orderJson.isNull("address") ? null : orderJson.getString("address");
        String paymentMethod = orderJson.isNull("paymentMethod") ? null : orderJson.getString("paymentMethod");
        String totalAmount = orderJson.isNull("totalAmount") ? null : orderJson.getString("totalAmount");

        Integer couponId =  orderJson.getInt("coupon");
        
        Optional<Couponowner>optionall = couponownerRepository.findById(couponId);
        Couponowner couponowner =optionall.get();
    

        Orders order = new Orders();
        order.setUsers(user);
        order.setShipname(recipientName);
        order.setShipphone(recipientPhone);
        order.setShipment(shippingMethod);
        order.setShipaddress(address);
        order.setPayment(paymentMethod);
        order.setTotalAmount(totalAmount);
        order.setCouponowner(couponowner);
        order.setStatus("未出貨");
        Orders savedOrder = ordersRepository.save(order);

        String ordernum = generateOrderNum(savedOrder.getId());
        savedOrder.setOrdernum(ordernum);
        ordersRepository.save(savedOrder);

        JSONArray orderDetailsJson = obj.getJSONArray("orderDetails");
        
        
        for (int i = 0; i < orderDetailsJson.length(); i++) {
            JSONObject detailJson = orderDetailsJson.getJSONObject(i);
            Integer cartId =  detailJson.getInt("cartId");
		        Optional<Cart>optionalll = cartRepository.findById(cartId);
		        Cart cart =optionalll.get();
          Orderdetail orderDetail = new Orderdetail();
            orderDetail.setOrders(savedOrder);
	        orderDetail.setCart(cart);   
	        
	        orderDetailRepository.save(orderDetail);
        }
        
        
             
    }
    @Transactional
    private String generateOrderNum(Integer orderId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        return dateStr + orderId;
    }
    
 
//    public Orders saveOrder(Orders order) {
//        return ordersRepository.save(order);
//    }
    @Transactional
    public Orders createOrder(Users user, List<Cart> cartItems, String shipAddress, String shipName, String shipPhone, String shipping, Integer totalAmount) {
        Orders order = new Orders();
        order.setUsers(user);
        order.setStatus("Pending"); // 初始状态
        order.setCreated_at(new Date());
        //order.setTotalAmount(totalAmount); // 设置总金额

        ordersRepository.save(order);

        for (Cart cart : cartItems) {
            Orderdetail orderDetail = new Orderdetail();
            orderDetail.setCart(cart);
            orderDetailRepository.save(orderDetail);

            cart.setStatus(1); // 将购物车商品状态改为1
            cartRepository.save(cart);

            order.getOrderdetail().add(orderDetail); // 将订单明细加入订单
        }

        return ordersRepository.save(order); // 确保保存了订单
    }

    @Transactional
    public List<Cart> getCartItemsForUser(Integer userId) {
        return cartRepository.findByUsersIdAndStatus(userId, 0);
    }
    @Transactional
	public void updateCartItems(List<Cart> selectedCartItems) {
		
	}

}
