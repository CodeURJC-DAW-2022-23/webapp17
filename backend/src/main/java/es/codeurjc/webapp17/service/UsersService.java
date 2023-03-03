package es.codeurjc.webapp17.service;


import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.conditional.ElseAction;
import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.Credential;
import es.codeurjc.webapp17.model.ProfileImage;
import es.codeurjc.webapp17.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.repository.UsersRepo;
import jakarta.annotation.PostConstruct;

@Service
public class UsersService{
    
    public static final String AUTOPASSWORD = "23334e79-c3e0-4dd7-942a-0b2567e09d9c";

    @Autowired
    private UsersRepo users;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        users.saveAndFlush(new UserProfile("auto-user", "Auto", passwordEncoder.encode(AUTOPASSWORD)));
    }

    public UsersRepo getUsers() {
        return users;
    }

    public UserProfile getUser(String email){
        List<UserProfile> profile = getUsers().findByEmail(email);
        if(profile.isEmpty()) return null;
        return profile.get(0);
    }

    public void registerUser(String email, String password, String name){
        registerUserWithProvider(email, name, passwordEncoder.encode(password), Credential.INTERNALSTRING);
    }
    
    public void registerUserWithProvider(String email, String name, String hash, String provider){
        UserProfile user = new UserProfile();
        user.setName(name);
        user.setEmail(email);
        user.setCart(new Cart(user));
        user.updateCredential(provider, hash);
        user.setEmailValidated(UUID.randomUUID().toString().replace("_", "-"));
        users.saveAndFlush(user);
    }

    public Map<String, Object> getUserInfo(String email){
        List<UserProfile> profile = getUsers().findByEmail(email);
        if(profile.isEmpty()) return null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", profile.get(0).getName());
        if(profile.get(0).getCart() != null)
            map.put("cart_length", profile.get(0).getCart().totalSize());
        return map;
    }

    public Object changeImage(String email, Blob newImage){
        UserProfile profile = getUser(email);
        if(profile == null) return null;
        profile.setImage(new ProfileImage(newImage, profile));
        users.saveAndFlush(profile);
        return true;
    }

    public Object changeName(String email, String newName){
        UserProfile profile = getUser(email);
        if(profile == null) return null;
        profile.setName(newName);
        users.saveAndFlush(profile);
        return true;
    }

    public Object changeDescription(String email, String newDescription){
        UserProfile profile = getUser(email);
        if(profile == null) return null;
        profile.setBio(newDescription);
        users.saveAndFlush(profile);
        return true;
    }
    
    public Object changePassword(String email, String newPassword){
        UserProfile profile = getUser(email);
        if(profile == null) return null;
        profile.updateCredential(Credential.INTERNALSTRING, passwordEncoder.encode(newPassword));
        users.saveAndFlush(profile);
        return true;
    }

    public Object verifyUserEmail(String email, String code){
        UserProfile profile = getUser(email);
        if(profile == null || !profile.getEmailValidated().equals(code) || code == "") return null;
        profile.setEmailValidated("true");
        users.saveAndFlush(profile);
        return true;
    }

    public void sendRegistrationEmail(String dest){
        UserProfile profile = getUser(dest);
        if(profile == null || 
        profile.getEmailValidated().isBlank()|| 
        profile.getEmailValidated().equals("true"))
            return;
        sendEmail(dest, "You created a Gustosa account", 
        "Thanks for enjoying Caipirinha as much as we do and creating and account with us your email verification code is "+
        "https://gustosa/verify?code="+profile.getEmailValidated()+"&"+"user="+profile.getEmail());
    }

    public boolean sendForgotPassword(String dest){
        UserProfile profile = getUser(dest);
        if(profile == null || 
        !profile.getEmailValidated().equals("true"))
            return false;
        profile.setForgotPassword(UUID.randomUUID().toString().replace("_", "-"));
        users.saveAndFlush(profile);
        sendEmail(dest, "Gustosa password change", 
        "To change your password access the following link: "+
        "https://gustosa/forgotPassword?code="+profile.getForgotPassword()+"&"+"user="+profile.getEmail());
        return true;
    }

    public Object verifyForgotPassword(String email, String code){
        UserProfile profile = getUser(email);
        if(profile == null || !profile.getForgotPassword().equals(code) || code == "") return null;
        profile.setForgotPassword("");
        users.saveAndFlush(profile);
        return true;
    }

    public void sendEmail(String dest, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dest); 
        message.setSubject(subject); 
        message.setText(content);
        try{
            emailSender.send(message);
        }catch(MailAuthenticationException ex){

        }
    }

    public boolean removeUser(String email){
        List<UserProfile> profile = getUsers().findByEmail(email);
        if(profile.isEmpty()) return false;
        try{
            users.delete(profile.get(0));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
}
