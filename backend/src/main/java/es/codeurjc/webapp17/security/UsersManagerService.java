package es.codeurjc.webapp17.security;

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
        UserProfile userProfile = null;
        try {
            if (users.findByEmail(username).isEmpty()) 
            throw new UsernameNotFoundException("Given user does not exist");
        
        userProfile = users.findByEmail(username).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userProfile.toUser();
    }

}
