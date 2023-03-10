package es.codeurjc.webapp17.tools;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.CharsetDecoder;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.FileCopyUtils;

import ch.qos.logback.core.boolex.Matcher;
import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Image;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

public class Tools {
    public static enum HashMethod{
        MD5("MD5"), SHA_1("SHA-1");
        private String code;
        HashMethod(String code){
            this.code = code;
        }
        public String getCode() {
            return code;
        }
    }

    public static enum Role{
        ADMIN("ADMIN"), USER("USER"), NONE("NONE"), AUTH("AUTH");
        private String code;
        Role(String code){
            this.code = code;
        }
        public String getCode() {
            return code;
        }
        
    }

    public static Blob resourceToBlob(String resource) throws IOException{
        Resource image = new ClassPathResource(resource);
        return BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
    }

    public static Logger getLogger(Class baseClass){
        return LoggerFactory.getLogger(baseClass);
    }

    public static String tr(String code, String locale){
        return java.util.ResourceBundle
                .getBundle("lang/"+locale)
                .getString(code);
    }

    public static PDDocument getConfirmationPdf(Cart cart) throws IOException{
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 24);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 725);
        contentStream.setLeading(14.5f);
        contentStream.showText("Pedido confirmado!");
        contentStream.newLine();
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 725);
        contentStream.newLine();
        contentStream.setFont(PDType1Font.COURIER, 12);
        for(CartItem item : cart.getCartItems()){
            contentStream.showText(item.getProduct().getTitle()+" x "+item.getQuantity()+" "+item.getQuantity()*item.getProduct().getPrice());
            contentStream.newLine();
        }
        if(cart.hasDiscount()){
            contentStream.showText("Con descuento: "+cart.getCoupon().getCode()+" del "+cart.getCoupon().getDiscount()+"%");
            contentStream.newLine();
        }
        contentStream.showText("Precio total es "+cart.totalPrice());
        contentStream.endText();
        contentStream.close();
        return document;
    }

    public static void sendEmail(String dest, String subject, String content, JavaMailSender emailSender){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dest); 
        message.setSubject(subject); 
        message.setText(content);
        try{
            emailSender.send(message);
        }catch(MailAuthenticationException ex){

        }
    }

    public static void sendEmailWithAttachment(String dest, String subject, String content, String name, ByteArrayDataSource attach, JavaMailSender emailSender) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
     
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(dest);
        helper.setSubject(subject);
        helper.setText(content);
            
        helper.addAttachment(name, attach);

        emailSender.send(message);
    }


    public static class PaginationMustache{

        private boolean hasMore;

        private Object elements;

        private int page;

        public PaginationMustache(boolean hasMore, Object elements, int page){
            this.hasMore = hasMore;
            this.elements = elements;
            this.page = page;
        }

    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
