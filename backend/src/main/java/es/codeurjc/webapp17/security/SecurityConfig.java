package es.codeurjc.webapp17.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RequestMappingHandlerMapping handler_mapping;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public UserDetailsService users() {
        return new UsersManagerService();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.csrf().disable().oauth2Client().and()
        .headers().frameOptions().sameOrigin();
        http.oauth2Login().loginPage("/login")
        .defaultSuccessUrl("/loginSuccess");

        // Handle NeedsSecurity annotation
        handler_mapping.getHandlerMethods().forEach((k, v)->{
            if(v.getMethod().isAnnotationPresent(NeedsSecurity.class)){
                NeedsSecurity sec = v.getMethod().getAnnotation(NeedsSecurity.class);
                for(String path_value : k.getPatternValues()){
                    path_value = path_value.replaceAll("\\{}.*\\}", "**");
                    try {
                        switch(sec.role()){
                            case NONE:
                                http.csrf().ignoringRequestMatchers(path_value).and()
                                .authorizeHttpRequests().requestMatchers(path_value).permitAll();
                                break;
                            case AUTH:
                                http.csrf().ignoringRequestMatchers(path_value).and()
                                .authorizeHttpRequests().requestMatchers(path_value).authenticated();
                                break;
                            default:
                                http.authorizeHttpRequests().requestMatchers(path_value).hasAuthority(sec.role().getCode());
                                break;
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        http.csrf().disable();

        // Permit every other request
        http.authorizeHttpRequests().anyRequest().permitAll();



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
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico", "/h2-console/**");
    }
}
