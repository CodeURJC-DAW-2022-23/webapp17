package es.codeurjc.webapp17.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.CartItemsRepo;
import es.codeurjc.webapp17.repository.CartsRepo;

@Service
public class CartsService {
    
    @Autowired
    private CartItemsRepo cartItems;

    @Autowired
    private CartsRepo carts;

    public CartItemsRepo getCartItemsRepo(){
        return cartItems;
    }

    public CartsRepo getCarts() {
        return carts;
    }

    public Page<Cart> getUserOrders(UserProfile user, int numPage, int pageSize){
        Pageable pageable = PageRequest.of(numPage, pageSize);
        return getCarts().findByCreatedByAndStatus(user, Cart.STATUS_ORDERED, pageable);
    }

    public Page<Cart> getUserOrders(UserProfile user){
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return getCarts().findByCreatedByAndStatus(user, Cart.STATUS_ORDERED, pageable);
    }
}