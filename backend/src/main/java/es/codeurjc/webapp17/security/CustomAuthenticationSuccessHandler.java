package es.codeurjc.webapp17.security;

import java.io.IOException;
import java.nio.file.ProviderNotFoundException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.Credential;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    
    @Autowired
    private UsersService users;


    public CustomAuthenticationSuccessHandler(){
        setDefaultTargetUrl("/profile");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication_normal) throws ServletException, IOException {
        if(authentication_normal instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken)authentication_normal;
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
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
            
            authentication_normal = new UsernamePasswordAuthenticationToken(email, null, euser.get(0).toUser().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication_normal);
        }
        
        

        super.onAuthenticationSuccess(request, response, authentication_normal);
    }
}
