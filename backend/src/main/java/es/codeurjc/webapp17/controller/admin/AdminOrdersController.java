package es.codeurjc.webapp17.controller.admin;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.Post;
import es.codeurjc.webapp17.repository.CartsRepo;
import es.codeurjc.webapp17.repository.PostRepo;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminOrdersController {

    @Autowired
    CartsRepo cartsRepo;

    @GetMapping("/adminOrders")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public String menu(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        Page<Cart> carts = cartsRepo
            .findByStatusNot(Cart.STATUS_NEW, PageRequest.of(page, pageSize));
        model.addAttribute("prevPag", (int)Math.max(0, page-1));
        int num = (int)Math.ceil((float)carts.getSize() / (float)pageSize);
        model.addAttribute("nextPag", (int)Math.min(page+1, num-1));
        
        
        if(!carts.isEmpty()){
            model.addAttribute("hasCarts", true);
            model.addAttribute("orders", carts);
        }
        return "admin/orders";
    }

    @PostMapping("/adminOrders")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity postMenu(Model model, @RequestParam(name = "id") Long id, 
        @RequestParam(name = "action") int action, HttpServletRequest request) {
        if(action==1){
            cartsRepo.deleteById(id);
        }else{
            Cart c = cartsRepo.findById(id).get();
            c.setStatus(Cart.STATUS_DONE);
            cartsRepo.saveAndFlush(c);
        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminOrders")).build();
    }
}
