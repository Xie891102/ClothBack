package com.cloth.Controller;

import com.cloth.Repository.CartRepository;
import com.cloth.Repository.ProductRepository;
import com.cloth.Repository.UsersRepository;
import com.cloth.dto.CartItemDto;
import com.cloth.model.Cart;
import com.cloth.model.Product;
import com.cloth.model.Users;
import com.cloth.service.CartService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Base64;


@RestController
@RequestMapping("/api/carts")
@CrossOrigin
public class CartController {
	
	@Autowired
    private final CartService cartService;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsersRepository usersRepository;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }
    
 // 用會員id找購物車
    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getCartsByUserId(@PathVariable Integer userId) {
        List<Cart> carts = cartService.findCartsByUserId(userId);

        //  JSON 響應
        JSONArray responseArray = new JSONArray();
        for (Cart cart : carts) {
            JSONObject cartJson = new JSONObject();
            cartJson.put("id", cart.getId());
            cartJson.put("quantity", cart.getQuantity());
            cartJson.put("status", cart.getStatus());
            
            // 商品信息
            Product product = cart.getProduct();
            JSONObject productJson = new JSONObject();
            productJson.put("id", product.getId());
            productJson.put("name", product.getName());
            productJson.put("description", product.getDescription());
            productJson.put("activity", product.getActivity().getName());
            productJson.put("quantity", product.getQuantity());
            productJson.put("size", product.getSize());
            productJson.put("price", product.getPrice());
            productJson.put("discount_price", product.getDiscount_price());
            productJson.put("img", Base64.getEncoder().encodeToString(product.getImg()));
            productJson.put("img2", Base64.getEncoder().encodeToString(product.getImg2()));
            productJson.put("img3", Base64.getEncoder().encodeToString(product.getImg3()));
            productJson.put("img4", Base64.getEncoder().encodeToString(product.getImg4()));
            productJson.put("status", product.getStatus());
            productJson.put("color", product.getColor());
            productJson.put("type", product.getType());
            productJson.put("gender", product.getGender());
            productJson.put("style", product.getStyle());

            cartJson.put("product", productJson);
            responseArray.put(cartJson);
        }

        return ResponseEntity.ok(responseArray.toString());
    }

    //用購物車id刪除商品
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer cartId) {
        cartService.deleteCartItem(cartId);
        return ResponseEntity.ok("Deleted cart item with id: " + cartId);
    }

    
    //修改商品數量
    @PutMapping("/quantity/{cartId}")
    public ResponseEntity<String> updateCartItemQuantity(
            @PathVariable Integer cartId,
            @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");

        // 检查数量是否合法
        if (quantity == null || quantity < 1) {
            return ResponseEntity.badRequest().body("Invalid quantity");
        }

        // 更新购物车项并计算小计
        Cart updatedCartItem = cartService.updateCartItemQuantity(cartId, quantity);

        // JSON 响应
        JSONObject responseJson = new JSONObject();
        responseJson.put("cartId", updatedCartItem.getId());
        responseJson.put("productId", updatedCartItem.getProduct().getId());
        responseJson.put("quantity", updatedCartItem.getQuantity());
        responseJson.put("subtotal", updatedCartItem.getSubtotal());

        return ResponseEntity.ok(responseJson.toString());
    }
    
    
    //更改status 
    //unchecked
    @PutMapping("/status")
    public void updateCartStatus(@RequestBody List<Integer> ids) {
        cartService.updateCartStatusToTwo(ids);
    }
    //checked
    @PutMapping("/status2")
    public void updateCartStatus2(@RequestBody List<Integer> ids) {
        cartService.updateCartStatusToZero(ids);
    }
    //already buy
    @PutMapping("/status3")
    public void updateCartStatus3(@RequestBody List<Integer> ids) {
        cartService.updateCartStatusToOne(ids);
    }
    
  //新增商品進購物車
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDto cartItemDto) {
        Optional<Product> productOptional = productRepository.findById(cartItemDto.getProductId());
        Optional<Users> userOptional = usersRepository.findById(cartItemDto.getUsersId());

        if (!productOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // 查找是否已有符合条件的购物车项
        Cart existingCart = cartRepository.findByProductIdAndUserIdAndStatusNot(
                cartItemDto.getProductId(), cartItemDto.getUsersId(), 1);

        if (existingCart != null) {
            // 更新现有购物车项的数量和小计
            existingCart.setQuantity(existingCart.getQuantity() + cartItemDto.getQuantity());
            existingCart.setSubtotal(existingCart.getProduct().getDiscount_price() * existingCart.getQuantity());
            existingCart.setUpdated_at(new Date());
            Cart savedCart = cartRepository.save(existingCart);
            return ResponseEntity.ok(savedCart);
        } else {
            // 创建并设置新的购物车项
            Integer discountPrice = cartItemDto.getDiscountPrice();
            Integer quantity = cartItemDto.getQuantity();
            Integer subtotal = discountPrice * quantity;

            Cart cart = new Cart();
            cart.setProduct(productOptional.get());
            cart.setUsers(userOptional.get());
            cart.setQuantity(quantity);
            cart.setStatus(cartItemDto.getStatus());
            cart.setSubtotal(subtotal);
            cart.setUpdated_at(new Date());

            Cart savedCart = cartRepository.save(cart);
            return ResponseEntity.ok(savedCart);
        }
    }

    
    //新增商品進購物車
//    @PostMapping("/add")
//    public ResponseEntity<String> addProductToCart(@RequestBody Map<String, Integer> request) {
//        Integer userId = request.get("userId");
//        Integer productId = request.get("productId");
//        Integer quantity = request.get("quantity");
//        Cart newCartItem = cartService.addProductToCart(userId, productId, quantity);
//
//        //  JSON 響應
//        JSONObject responseJson = new JSONObject();
//        //responseJson.put("message", "商品已成功添加到購物車");
//        responseJson.put("cartId", newCartItem.getId());
//        responseJson.put("productId", newCartItem.getProduct().getId());
//        responseJson.put("quantity", newCartItem.getQuantity());
//
//        return ResponseEntity.ok(responseJson.toString());
//    }
    }
