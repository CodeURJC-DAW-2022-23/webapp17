package es.codeurjc.webapp17.service;


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
import es.codeurjc.webapp17.model.Credential;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.UsersRepo;
import jakarta.annotation.PostConstruct;

@Service
public class UsersService{
    
    public static final String AUTO_PASSWORD = "23334e79-c3e0-4dd7-942a-0b2567e09d9c";

    @Autowired
    private UsersRepo users;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        users.save(new UserProfile("auto-user", "Auto", "23334e79-c3e0-4dd7-942a-0b2567e09d9c"));
    }

    public UsersRepo getUsers() {
        return users;
    }

    public void registerUser(String email, String password, String name){
        registerUserWithProvider(email, name, passwordEncoder.encode(password), Credential.INTERNAL_STRING);
    }
    
    public void registerUserWithProvider(String email, String name, String hash, String provider){
        UserProfile user = new UserProfile();
        user.setName(name);
        user.setEmail(email);
        user.updateCredential(provider, hash);
        user.setEmailValidated(UUID.randomUUID().toString().replace("_", "-"));
        users.saveAndFlush(user);
    }

    public Map<String, Object> getUserInfo(String email){
        List<UserProfile> profile = getUsers().findByEmail(email);
        if(profile.isEmpty()) return null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", profile.get(0).getName());
        return map;
    }
    
    public Object changePassword(String email, String new_password){
        List<UserProfile> profile = getUsers().findByEmail(email);
        if(profile.isEmpty()) return null;
        profile.get(0).updateCredential(Credential.INTERNAL_STRING, passwordEncoder.encode(new_password));
        users.saveAndFlush(profile.get(0));
        return true;
    }

    public Object verifyUserEmail(String email, String code){
        List<UserProfile> profile = getUsers().findByEmail(email);
        if(profile.isEmpty() || !profile.get(0).getEmailValidated().equals(code) || code == "") return null;
        profile.get(0).setEmailValidated("true");
        users.saveAndFlush(profile.get(0));
        return true;
    }

    public void sendRegistrationEmail(String dest){
        List<UserProfile> profile = getUsers().findByEmail(dest);
        if(profile.isEmpty() || 
        profile.get(0).getEmailValidated().isBlank()|| 
        profile.get(0).getEmailValidated().equals("true"))
            return;
        sendEmail(dest, "You created a Gustosa account", 
        "Thanks for enjoying Caipirinha as much as we do and creating and account with us your email verification code is "+
        "https://gustosa/verify?code="+profile.get(0).getEmailValidated()+"&"+"user="+profile.get(0).getEmail());
    }

    public boolean sendForgotPassword(String dest){
        List<UserProfile> profile = getUsers().findByEmail(dest);
        if(profile.isEmpty() || 
        !profile.get(0).getEmailValidated().equals("true"))
            return false;
        profile.get(0).setForgotPassword(UUID.randomUUID().toString().replace("_", "-"));
        users.saveAndFlush(profile.get(0));
        sendEmail(dest, "Gustosa password change", 
        "To change your password access the following link: "+
        "https://gustosa/forgotPassword?code="+profile.get(0).getForgotPassword()+"&"+"user="+profile.get(0).getEmail());
        return true;
    }

    public Object verifyForgotPassword(String email, String code){
        List<UserProfile> profile = getUsers().findByEmail(email);
        if(profile.isEmpty() || !profile.get(0).getForgotPassword().equals(code) || code == "") return null;
        profile.get(0).setForgotPassword("");
        users.saveAndFlush(profile.get(0));
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
}
