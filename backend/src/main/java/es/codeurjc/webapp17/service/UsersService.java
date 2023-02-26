package es.codeurjc.webapp17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.repository.UsersRepo;

@Service
public class UsersService{
    
    @Autowired
    private UsersRepo users;

    public UsersRepo getUsers() {
        return users;
    }

    public void createUser(){

    }
    

}
