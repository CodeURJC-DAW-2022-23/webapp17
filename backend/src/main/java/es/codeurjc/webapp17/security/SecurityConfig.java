package es.codeurjc.webapp17.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import es.codeurjc.webapp17.tools.Tools.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private RequestMappingHandlerMapping handler_mapping;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        handler_mapping.getHandlerMethods().forEach((k, v)->{
            if(v.getMethod().isAnnotationPresent(NeedsSecurity.class)){
                NeedsSecurity sec = v.getMethod().getAnnotation(NeedsSecurity.class);
                for(String path_value : k.getPatternValues()){
                    path_value = path_value.replaceAll("\\{}.*\\}", "**");
                    try {
                        if(sec.role() == Role.NONE){
                            http.csrf().ignoringRequestMatchers(path_value).and()
                            .authorizeHttpRequests().requestMatchers(path_value).permitAll();
                        }else{
                            http.authorizeHttpRequests().requestMatchers(path_value).hasRole(sec.role().getCode());
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        http.authorizeHttpRequests().anyRequest().hasRole(Tools.Role.ADMIN.getCode());

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password_hash");
        http.formLogin().defaultSuccessUrl("/");
        http.formLogin().failureUrl("/register");

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
    }

}
