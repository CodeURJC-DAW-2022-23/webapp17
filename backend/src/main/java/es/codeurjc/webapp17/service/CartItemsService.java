package es.codeurjc.webapp17.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.codeurjc.webapp17.repository.CartItemsRepo;

@Service
public class CartItemsService {
    
    @Autowired
    private CartItemsRepo cartItems;

    public CartItemsRepo getCartItemsRepo(){
        return cartItems;
    }
}