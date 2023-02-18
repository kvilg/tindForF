package com.example.demo.repo;

import com.example.demo.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo  extends JpaRepository<UserLogin,Long> {

    public UserLogin findByLogin(String login);

    public UserLogin getByLogin(String login);

    public List<UserLogin> getByName(String login);

}
