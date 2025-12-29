package com.harshith.journalapp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
//@Data
//@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
//    @NonNull
    private String username;
//    @NonNull
    private String password;

    private List<Journal> journalEntries=new ArrayList<>();

    private List<String> roles;

    public User(ObjectId id, String username, String password, List<Journal> journalEntries,List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.journalEntries = journalEntries;
        this.roles=roles;
    }
    public User(){

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public  String getUsername() {
        return username;
    }

    public void setUsername( String username) {
        this.username = username;
    }

    public  String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public void setJournalEntries(List<Journal> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public List<Journal> getJournalEntries() {
        return journalEntries;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
