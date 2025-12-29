package com.harshith.journalapp.service;

import com.harshith.journalapp.entity.Journal;
import com.harshith.journalapp.entity.User;
import com.harshith.journalapp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void save(Journal journal,String username){
        journal.setDate(LocalDateTime.now());
        journalRepository.save(journal);
        User user=userService.findByUserName(username);
        user.getJournalEntries().add(journal);
        userService.save(user);
    }

    public void save(Journal journal){
        journalRepository.save(journal);
    }

    public Optional<Journal> findById(ObjectId id){
        return journalRepository.findById(id);
    }

    @Transactional
    public void delete(ObjectId id, String username){
        User user=userService.findByUserName(username);
        boolean removed=user.getJournalEntries().removeIf(j->j.getId().equals(id));
        if(removed){
            userService.save(user);
            journalRepository.deleteById(id);
        }
    }

    public List<Journal> findAll(String username){
        User user=userService.findByUserName(username);
        return user.getJournalEntries();
    }
}
