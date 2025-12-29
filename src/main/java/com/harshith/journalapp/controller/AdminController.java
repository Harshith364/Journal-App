package com.harshith.journalapp.controller;

import com.harshith.journalapp.entity.User;
import com.harshith.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> allUsers(){
        try{
            List<User> users=userService.findAll();
            return ResponseEntity.ok().body(users);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> addAdmin(@RequestBody User user){
        try{
            userService.saveNewAdmin(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
