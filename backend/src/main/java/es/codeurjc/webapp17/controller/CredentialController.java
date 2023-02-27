package es.codeurjc.webapp17.controller;

import java.nio.file.ProviderNotFoundException;
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

    private static String authorizationRequestBaseUri
      = "/oauth2/authorization/";

    private static String CLIENT_PROPERTY_KEY 
    = "spring.security.oauth2.client.registration.";

    @Autowired
    private Environment env;

    @Autowired
    private UsersService users;


    private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(
        CLIENT_PROPERTY_KEY + client + ".client-id");

        if (clientId == null) {
            return null;
        }

        String clientSecret = env.getProperty(
        CLIENT_PROPERTY_KEY + client + ".client-secret");
    
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
        ClientRegistration google_reg = getRegistration("google");
        Cookie cookie = new Cookie("sign-target", "login");
        response.addCookie(cookie);
        model.addAttribute("google-login", authorizationRequestBaseUri+google_reg.getRegistrationId());
        return "login/login";
    }

    private Object loginFromOAuth2(RedirectAttributes attributes, OAuth2AuthenticationToken authentication, HttpServletRequest request, HttpServletResponse response){
        OAuth2User user = authentication.getPrincipal();
        String email = user.getAttribute("email");
        List<UserProfile> euser = users.getUsers().findByEmail(email);
        if(euser.isEmpty()){
            String username = user.getAttribute("name").toString().isBlank() ? 
                UUID.randomUUID().toString().replace("_", "") : user.getAttribute("name");
            try {
                users.registerUserWithProvider(email, username, user.getName(), authentication.getAuthorizedClientRegistrationId());
                euser = users.getUsers().findByEmail(email);
                users.sendRegistrationEmail(email);
            } catch (Exception e) {
                throw e;
            }
        }
        
        Credential cred = euser.get(0).getCredential(authentication.getAuthorizedClientRegistrationId());
        
        if(cred == null)
            throw new ProviderNotFoundException("User not register with provider.");
        
        Authentication auth = new UsernamePasswordAuthenticationToken(email, null, euser.get(0).toUser().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView("/profile");
    }

    @NeedsSecurity(role=Tools.Role.NONE)
    @GetMapping("/loginSuccess")
    public Object getLoginInfo(RedirectAttributes attributes, OAuth2AuthenticationToken authentication, HttpServletRequest request, HttpServletResponse response) {
        
        if(!Arrays.stream(request.getCookies()).anyMatch(x -> x.getName().equals("sign-target")))
            throw new ProviderNotFoundException("Can't access login");
        
        // Remove cookie
        Cookie cookie = new Cookie("sign-target", "none");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        Optional<Cookie> p = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("sign-target")).findFirst();
        
        switch(p.get().getValue()){
            case "login":
                return loginFromOAuth2(attributes, authentication, request, response);
            default:
                throw new ProviderNotFoundException("Something went wrong.");
        }
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
        if(!users.getUserInfo(email).containsKey("error")){
            users.sendForgotPassword(email);
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
            request.login("auto-user", users.AUTO_PASSWORD);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        UserProfile user_profile = users.getUsers().findByEmail(user).get(0);
        Authentication auth = new UsernamePasswordAuthenticationToken(user_profile.getEmail(), null, user_profile.toUser().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "login/changepassword";
    }

    @GetMapping("/changePassword")
    @NeedsSecurity(role = Tools.Role.USER)
    public Object changePassword(HttpServletRequest request){
        return "login/changepassword";
    }

    @PostMapping("/changePassword")
    @NeedsSecurity(role = Tools.Role.USER)
    public @ResponseBody Map<String,Object> changePasswordPost(HttpServletRequest request, 
    @RequestParam(name="new_password", required = true) String password){
        HashMap<String, Object> map = new HashMap<>();
        if(request.getUserPrincipal() != null){
            if(!users.getUserInfo(request.getUserPrincipal().getName()).containsKey("error")){
                users.changePassword(request.getUserPrincipal().getName(), password);
                map.put("changed", "true");
                return map;
            }
        }
        map.clear();
        map.put("error", "true");

        return map;
    }

}
