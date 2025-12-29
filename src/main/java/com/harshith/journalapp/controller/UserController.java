package com.harshith.journalapp.controller;

import com.harshith.journalapp.entity.User;
import com.harshith.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;



    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        if(username!=null){
            User dbUser=userService.findByUserName(username);
            dbUser.setUsername(user.getUsername());
            dbUser.setPassword(user.getPassword());
            userService.save(dbUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
