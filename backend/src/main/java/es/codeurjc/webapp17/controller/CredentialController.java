package es.codeurjc.webapp17.controller;

import java.nio.file.ProviderNotFoundException;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.model.Credential;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.UsersRepo;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CredentialController {

    private static String authorizationRequestBaseUri
      = "oauth2/authorization";

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
    public String login(Model model) {
        ClientRegistration reg = getRegistration("google");
        model.addAttribute("user-info", "");
        return "login/login";
    }

    @NeedsSecurity(role=Tools.Role.NONE)
    @GetMapping("/loginSuccess")
    public Object getLoginInfo(Model model, OAuth2AuthenticationToken authentication, HttpServletRequest request) {
        OAuth2User user = authentication.getPrincipal();
        String email = user.getAttribute("email");
        List<UserProfile> euser = users.getUsers().findByEmail(email);
        if(euser.isEmpty())
            throw new ProviderNotFoundException("Could not find given user. Are you registered?");
        Credential cred = euser.get(0).getCredential(authentication.getAuthorizedClientRegistrationId());
        if(cred == null)
            throw new ProviderNotFoundException("User not register with provider.");
        try {
            request.logout();
            Authentication auth = new UsernamePasswordAuthenticationToken(email, null, euser.get(0).toUser().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("user-info", request.getUserPrincipal());
        
        return "login/login";
    }

    @GetMapping("/register")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String register(Model model) {
        return "login/register";
    } 
}
