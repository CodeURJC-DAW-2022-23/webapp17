package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.service.AdminService;
import es.codeurjc.webapp17.tools.NeedsSecurity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String adminMenu(Model model) {
        HashMap<Product,Integer> totalSales = adminService.getTotalSales();
        List<Product> topProducts = obtainTopProducts(totalSales);
        List<String> topProductsName = new ArrayList<>();
        for (Product product : topProducts) {
            topProductsName.add(product.getTitle());
        }
        List<Integer> topSales = obtainTopSales(totalSales);
        boolean salesExist = false;
        model.addAttribute("topProductsName", topProductsName);
        model.addAttribute("topSales", topSales);
        if (topProducts.size()!=0){
            salesExist = true;
            model.addAttribute("salesExist", salesExist);
        } else {
            model.addAttribute("salesExist", salesExist);
        }
        
        return "/admin/dashboard";
    }

    public static List<Product> obtainTopProducts(Map<Product, Integer> totalSales) {
        List<Product> topProducts = new ArrayList<>();
        PriorityQueue<Entry<Product, Integer>> heapVentas = new PriorityQueue<>(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        for (Map.Entry<Product, Integer> entry : totalSales.entrySet()) {
            heapVentas.offer(entry);
        }
        
        for (int i = 0; i < 5 && !heapVentas.isEmpty(); i++) {
            topProducts.add(heapVentas.poll().getKey());
        }
        
        return topProducts;
    }
    
    public static List<Integer> obtainTopSales(HashMap<Product, Integer> totalSales) {
        List<Integer> topSales = new ArrayList<>();
        PriorityQueue<Integer> heapCantidad = new PriorityQueue<>(Comparator.reverseOrder());
        
        for (Integer cantidad : totalSales.values()) {
            heapCantidad.offer(cantidad);
        }
        
        for (int i = 0; i < 5 && !heapCantidad.isEmpty(); i++) {
            topSales.add(heapCantidad.poll());
        }
        
        return topSales;
    }

}
