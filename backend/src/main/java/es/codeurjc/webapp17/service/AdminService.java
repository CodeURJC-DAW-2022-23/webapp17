package es.codeurjc.webapp17.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Product;

@Service
public class AdminService {

    HashMap<Product,Integer> totalSales = new HashMap<>();

    public HashMap<Product, Integer> getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(HashMap<Product, Integer> totalSales) {
        this.totalSales = totalSales;
    }

    public void updateTotalSales(List<CartItem> listProductsSold) {
        for (CartItem cartItem : listProductsSold) {
            int quantity = cartItem.getQuantity();
            for (int i=0; i<quantity; i++){
                if (totalSales.containsKey(cartItem.getProduct())){
                    int previousSales = totalSales.get(cartItem.getProduct());
                    totalSales.put(cartItem.getProduct(), previousSales + 1);
                } else {
                    totalSales.put(cartItem.getProduct(), 1);
                }
            }
            
        }
    }
    

}
