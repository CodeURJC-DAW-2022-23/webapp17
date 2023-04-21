package es.codeurjc.webapp17.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.codeurjc.webapp17.model.Credential;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.ProviderNotFoundException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.hibernate.engine.spi.Resolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public UserDetailsService users() {
        return new UsersManagerService();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.csrf().disable().oauth2Client().and()
        .headers().frameOptions().sameOrigin();
        http.oauth2Login().loginPage("/login")
        .successHandler(customSuccessHandler());

        // Handle NeedsSecurity annotation
        handlerMapping.getHandlerMethods().forEach((k, v)->{
            if(v.getMethod().isAnnotationPresent(NeedsSecurity.class)){
                NeedsSecurity sec = v.getMethod().getAnnotation(NeedsSecurity.class);
                for(String path_value : k.getPatternValues()){
                    path_value = path_value.replaceAll("\\{.*\\}", "\\d+");
                    int er = 0;
                    try {
                        HttpMethod method = HttpMethod.valueOf(k.getMethodsCondition().getMethods().iterator().next().name());
                        switch(sec.role()){
                            case NONE:
                                http.csrf().ignoringRequestMatchers(path_value).and()
                                .authorizeHttpRequests().requestMatchers(method, path_value).permitAll();
                                break;
                            case AUTH:
                                http.csrf().ignoringRequestMatchers(path_value).and()
                                .authorizeHttpRequests().requestMatchers(method, path_value).authenticated();
                                break;
                            default:
                                http.authorizeHttpRequests().requestMatchers(method, path_value).hasAuthority(sec.role().getCode());
                                break;
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        http.csrf().ignoringRequestMatchers("/api/**");
        http.csrf().disable();

        // Permit every other request
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.exceptionHandling(handling -> handling.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"){
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                                         AuthenticationException authException) throws IOException, ServletException {
                String in = request.getRequestURI();
                if (in.startsWith("/api")) {
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Forbidden api call");
                } else {
                    super.commence(request, response, authException);
                }
            }
        }));
        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/profile");

        http.logout()
        .deleteCookies("JSESSIONID");

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/new/**","/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico", "/h2-console/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html");
    }
}
