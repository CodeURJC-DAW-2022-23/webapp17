package es.codeurjc.webapp17.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.UsersRepo;
import jakarta.annotation.PostConstruct;

@Service
public class UsersService{
    
    @Autowired
    private UsersRepo users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        users.save(new UserProfile("jorgevegarias1@gmail.com", 
        "test", passwordEncoder.encode("test")));
    }

    public UsersRepo getUsers() {
        return users;
    }

    public void createUser(){

    }
    

}
