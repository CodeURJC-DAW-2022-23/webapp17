package es.codeurjc.webapp17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.UsersRepo;
import jakarta.annotation.PostConstruct;

@Service
public class UsersService {
    
    @Autowired
    private UsersRepo users;

    @PostConstruct
    public void init(){
        users.save(new UserProfile("Jhon Smith", "helloworld@email.com"));
    }

    public UsersRepo getUsers() {
        return users;
    }

}
