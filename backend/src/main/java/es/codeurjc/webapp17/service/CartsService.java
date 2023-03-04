package es.codeurjc.webapp17.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.tools.Tool;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.CartItemsRepo;
import es.codeurjc.webapp17.repository.CartsRepo;
import es.codeurjc.webapp17.repository.UsersRepo;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class CartsService {
    
    @Autowired
    private CartItemsRepo cartItems;

    @Autowired
    private CartsRepo carts;

    @Autowired
    private UsersRepo users;

    @Autowired
    private JavaMailSender emailSender;

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

    public void confirmOrder(Cart order){
        order.setStatus(Cart.STATUS_ORDERED);
        carts.saveAndFlush(order);

        UserProfile profile = order.getCreatedBy();
        profile.emptyCart();
        users.saveAndFlush(profile);

        sendOrderMail(order);
    }

    public void sendOrderMail(Cart order){
        try {
            PDDocument doc = Tools.getConfirmationPdf(order);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            doc.save(out);
            doc.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ByteArrayDataSource source =  new ByteArrayDataSource(out.toByteArray(), "application/octet-stream");
            Tools.sendEmailWithAttachment(order.getCreatedBy().getEmail(), 
            "Tu pedido ha sido confirmado", 
            "Aquí tienes el documento de confirmación: ", "invoice.pdf", source, emailSender);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}