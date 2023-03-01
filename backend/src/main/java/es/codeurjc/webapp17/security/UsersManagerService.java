package es.codeurjc.webapp17.security;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.UsersRepo;

public class UsersManagerService implements UserDetailsService{
  
    @Autowired
    private UsersRepo users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (users.findByEmail(username).isEmpty()) 
            throw new UsernameNotFoundException("Given user does not exist");
        UserProfile userProfile = users.findByEmail(username).get(0);
        return userProfile.toUser();
    }

}
