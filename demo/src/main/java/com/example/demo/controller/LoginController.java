package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.UserAuth;
import com.example.demo.model.UserOut;
import com.example.demo.security.JwtTokenUtil;
import com.solidfire.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.demo.service.UserService;
import static com.example.demo.model.Constants.TOKEN_PREFIX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService service;

    @PostMapping(path = "/login")
    public UserOut getAuthUser(@RequestBody UserAuth json) {


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(json.login, json.password));
        com.example.demo.model.User user = service.getByLogin(json.login);
        String token = jwtTokenUtil.generateToken(user);


        return new UserOut(user, TOKEN_PREFIX + token);
    }


    @GetMapping(path = "/login/jwt")
    public UserOut getUserByJWT(@RequestHeader("Authorization") String token) {


        String a = token.substring(TOKEN_PREFIX.length());
        String userLogin = jwtTokenUtil.getUsernameFromToken(a);

        User user = service.getByLogin(userLogin);

        return new UserOut(user);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<String> registration(@RequestBody String json) throws Exception {

        Gson gson = new Gson();
        UserAuth gsonUser = gson.fromJson(json, UserAuth.class);

        service.registration(gsonUser.name, gsonUser.login, gsonUser.password);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
