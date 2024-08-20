package com.cloth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloth.service.OrderDetailService;


@RestController
@RequestMapping("/api/orderdetail")
@CrossOrigin
public class OrderDetailController {
	
	@Autowired
	private OrderDetailService orderDetailService;
	
//	 // 用會員id找訂單詳情
//	 @GetMapping("/users/{userId}")
//	    public ResponseEntity<String> getCartsByUserId(@PathVariable Integer userId) {
//	        List<Orderdetail> orderdetails = orderDetailService.findOrderdetailByUserId(userId);
//
//	        //  JSON 響應
//	        JSONArray responseArray = new JSONArray();
//	        for (Orderdetail orderdetail : orderdetails) {
//	            JSONObject orderdetailJson = new JSONObject();
//	            orderdetailJson.put("id", orderdetail.getId());
//	            orderdetailJson.put("cart_id", orderdetail.getCart());
//	            orderdetailJson.put("returns_id", orderdetail.getReturns());
//	            orderdetailJson.put("orders_id", orderdetail.getOrders());
//	            
//	            // 商品信息
//	            Cart cart = orderdetail.getCart();
//	            JSONObject cartJson = new JSONObject();
//	            cartJson.put("id", cart.getId());
//	            cartJson.put("created_at", cart.getCreated_at());
//	            cartJson.put("quantity", cart.getQuantity());
//	            cartJson.put("updated_at", cart.getUpdated_at());
//	            cartJson.put("product_id", cart.getProduct());
//	            cartJson.put("users_id", cart.getUsers());
//	            cartJson.put("status", cart.getStatus());
//	            cartJson.put("subtotal", cart.getSubtotal());
//	   
//	            
//	            orderdetailJson.put("cart", cartJson);
//	            responseArray.put(orderdetailJson);
//	        }
//
//	        return ResponseEntity.ok(responseArray.toString());
//	    }

}
