package com.harshith.journalapp.controller;


import com.harshith.journalapp.entity.Journal;
import com.harshith.journalapp.entity.User;
import com.harshith.journalapp.service.JournalService;
import com.harshith.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/journals")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    private final Map<String,Journal> journals=new HashMap<>();

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournalsOfUser(){
//        List<Journal> list= (List<Journal>) journals.values();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        try{
           return ResponseEntity.ok().body(journalService.findAll(username));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Journal> add(@RequestBody Journal journal){
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            journalService.save(journal,username);
            return new ResponseEntity<>(journal, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{jid}")
    public ResponseEntity<Journal> update(@PathVariable ObjectId jid, @RequestBody Journal journal){
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            User user=userService.findByUserName(username);
            List<Journal> list=user.getJournalEntries().stream().filter(j->j.getId().equals(jid)).collect(Collectors.toList());
            if(!list.isEmpty()){
                Optional<Journal> old = journalService.findById(jid);
                if (old.isPresent()) {
                    Journal j=old.get();
                    j.setTitle(journal.getTitle());
                    j.setContent(journal.getContent());
                    journalService.save(j);
                    return ResponseEntity.ok().body(journal);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }

        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
//        journals.put(jid,journal);
//        return journals.get(jid);
        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/id/{jid}")
    public ResponseEntity<Journal> delete(@PathVariable ObjectId jid){
        try{
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            journalService.delete(jid,username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{jid}")
    public ResponseEntity<Journal> get(@PathVariable ObjectId jid){
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            User user=userService.findByUserName(username);
            List<Journal> list=user.getJournalEntries().stream().filter(j->j.getId().equals(jid)).collect(Collectors.toList());
            if(!list.isEmpty()){
                Optional<Journal> journal= journalService.findById(jid);
                return journal.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
