package com.harshith.journalapp.security;

import com.harshith.journalapp.entity.User;
import com.harshith.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser= userService.findByUserName(username);
        System.out.println(dbUser);
        if(dbUser!=null){
            UserDetails user;
            user = org.springframework.security.core.userdetails.User.withUsername(dbUser.getUsername())
                    .password(dbUser.getPassword())
                    .roles(dbUser.getRoles().toArray(new String[0]))
                    .build();
            System.out.println(user);
            System.out.println(user.getUsername());
            return user;
        }
        throw new UsernameNotFoundException("User Not Found");
    }


}
