package com.cloth.service;

import com.cloth.model.Cart;
import com.cloth.model.Product;
import com.cloth.model.Users;
import com.cloth.Repository.CartRepository;
import com.cloth.Repository.ProductRepository;
import com.cloth.Repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
	
//	@Autowired
//    private Product product;
	
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsersRepository usersRepository;


    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findCartsByUserId(Integer userId) {
        return cartRepository.findCartsByUserId(userId);
    }
    
    public void deleteCartItem(Integer cartId) {
        cartRepository.deleteById(cartId);
    }
    

    public Cart addProductToCart(Integer userId, Integer productId, Integer quantity) {
        Optional<Users> optionalUser = usersRepository.findById(userId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        
        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            Cart cartItem = new Cart();
            cartItem.setUsers(optionalUser.get());
            cartItem.setProduct(optionalProduct.get());
            cartItem.setQuantity(quantity);
            Date now = new Date();
            cartItem.setCreated_at(now);

            return cartRepository.save(cartItem);
        } else {
            throw new RuntimeException("User or Product not found with provided id");
        }
    }
    
//查找購物車
    public Cart updateCartItemQuantity(Integer cartId, Integer quantity) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cartItem = optionalCart.get();
            
            //取得商品ID
            Integer productId = cartItem.getProduct().getId();
            if (productId != null) {
                Optional<Product> optionalProduct = productRepository.findById(productId);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    Integer discount_price = product.getDiscount_price(); 
                    
                    Integer subtotal = discount_price * quantity;

                    cartItem.setQuantity(quantity);
                    cartItem.setSubtotal(subtotal);
                    cartItem.setUpdated_at(new Date());
                    
                    return cartRepository.save(cartItem);
                } else {
                    throw new RuntimeException("Product not found with id: " + productId);
                }
            } else {
                throw new RuntimeException("Product ID not found in cart item with id: " + cartId);
            }
        } else {
            throw new RuntimeException("Cart item not found with id: " + cartId);
        }
    }
    
    //更改status 
    public void updateCartStatusToTwo(List<Integer> ids) {
        ids.forEach(id -> cartRepository.updateStatusToTwo(id));
    }
    public void updateCartStatusToZero(List<Integer> ids) {
        ids.forEach(id -> cartRepository.updateStatusToZero(id));
    }
    public void updateCartStatusToOne(List<Integer> ids) {
        ids.forEach(id -> cartRepository.updateStatusToOne(id));
    }

}
