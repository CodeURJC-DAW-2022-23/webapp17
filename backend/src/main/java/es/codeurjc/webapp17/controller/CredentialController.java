package es.codeurjc.webapp17.controller;

import java.nio.file.ProviderNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.nimbusds.oauth2.sdk.Role;

import es.codeurjc.webapp17.model.Credential;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.mail.AuthenticationFailedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CredentialController {

    private static long SECONDS_COOLDOWN = 60*60;

    private static String authorizationRequestBaseUri
      = "/oauth2/authorization/";

    private static String CLIENTPROPERTYKEY 
    = "spring.security.oauth2.client.registration.";

    @Autowired
    private Environment env;

    @Autowired
    private UsersService users;


    private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(
        CLIENTPROPERTYKEY + client + ".client-id");

        if (clientId == null) {
            return null;
        }

        String clientSecret = env.getProperty(
        CLIENTPROPERTYKEY + client + ".client-secret");
    
        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
            .clientId(clientId).clientSecret(clientSecret).build();
        }
        if (client.equals("github")) {
            return CommonOAuth2Provider.GITHUB.getBuilder(client)
            .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }


    @GetMapping("/login")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(name="error", required = false)String error) {
        ClientRegistration googleReg = getRegistration("google");
        model.addAttribute("google-login", authorizationRequestBaseUri+googleReg.getRegistrationId());
        return "login/login";
    }

    @GetMapping("/register")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String register(Model model) {
        return "login/register";
    }

    @PostMapping("/register")
    @NeedsSecurity(role=Tools.Role.NONE)
    public Object postRegister(HttpServletRequest request, @RequestParam(name="email", required = true)String email, 
    @RequestParam(name="password", required = true)String password, 
    @RequestParam(name="username", required = true) String username){
        try {
            users.registerUser(email, password, username);
            request.login(email, password);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
        return "login/register";
    }

    @GetMapping("/verify")
    @NeedsSecurity(role = Tools.Role.NONE)
    public Object verify(@RequestParam(name="code", required = true) String code, @RequestParam(name="user", required = true) String user){
        if(users.verifyUserEmail(user, code) == null)
            throw new RuntimeException("Failed to verify account");
        return "login/verified";
    }

    @PostMapping("/forgotPassword")
    @NeedsSecurity(role = Tools.Role.NONE)
    public @ResponseBody Map<String,Object> forgotPasswordPost(HttpServletRequest request, 
    @RequestParam(name="email", required = true) String email){
        HashMap<String, Object> map = new HashMap<>();
        if(users.getUserInfo(email).containsKey("error")){
            UserProfile user = users.getUsers().findByEmail(email).get(0);
            if(!user.getForgotPassword().equals("") 
            && user.getLastModified().before(Timestamp.from(Instant.now().minusSeconds(SECONDS_COOLDOWN)))){
                map.put("already", "true");
            }else if(users.sendForgotPassword(email))
                map.put("sended", "true");
            return map;
        }
        map.clear();
        map.put("error", "true");

        return map;
    }

    @GetMapping("/forgotPassword")
    @NeedsSecurity(role = Tools.Role.NONE)
    public Object forgotPassword(RedirectAttributes attributes, HttpServletRequest request, 
    @RequestParam(name="code", required = true) String code, @RequestParam(name="user", required = true) String user, HttpServletResponse response){
        if(users.verifyForgotPassword(user, code) == null)
            throw new RuntimeException("Failed to verify account");
        try {
            request.login("auto-user", users.AUTOPASSWORD);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        UserProfile userProfile = users.getUsers().findByEmail(user).get(0);
        Authentication auth = new UsernamePasswordAuthenticationToken(userProfile.getEmail(), null, userProfile.toUser().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "login/changepassword";
    }

    @GetMapping("/changePassword")
    @NeedsSecurity(role = Tools.Role.USER)
    public Object changePassword(HttpServletRequest request){
        return "login/changepassword";
    }

    @PostMapping("/user/changePassword")
    @NeedsSecurity(role = Tools.Role.USER)
    public @ResponseBody Map<String,Object> changePasswordPost(HttpServletRequest request, 
    @RequestParam(name="newPassword", required = true) String password){
        HashMap<String, Object> map = new HashMap<>();
        if(request.getUserPrincipal() != null){
            if(users.changePassword(request.getUserPrincipal().getName(), password) != null){
                map.put("changed", "true");
                return map;
            }
        }
        map.clear();
        map.put("error", "true");

        return map;
    }

}
