package com.example.demo.model;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

public class UserOut {

    private Long id;

    private String name;

    private String login;

    private String token ;

    public UserOut(User user,String token){
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.token = token;
    }

    public UserOut(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
    }
}
