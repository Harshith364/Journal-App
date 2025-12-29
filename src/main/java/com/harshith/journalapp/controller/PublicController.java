package com.harshith.journalapp.controller;

import com.harshith.journalapp.entity.User;
import com.harshith.journalapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PublicController {

    private final Logger logger= LoggerFactory.getLogger(PublicController.class);
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        userService.saveNew(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String> healthCheck(){
        logger.debug("okkkkkk");
        logger.info("okkkk");
        logger.error("errorrrr");
        return ResponseEntity.ok().body("check-ok");
    }
}
