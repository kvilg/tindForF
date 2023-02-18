package com.example.demo.model;

public class UserOut {

    private Long id;

    private String name;

    private String login;

    private String token ;

    public UserOut(UserLogin user, String token){
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.token = token;
    }

    public UserOut(UserLogin user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
    }
}
