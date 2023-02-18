package com.example.demo.service;


import com.example.demo.model.UserLogin;


import com.example.demo.repo.UserRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepo userData;


//    @PersistenceContext // or even @Autowired
//    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;



    public List<UserLogin> getAll() {
        return  this.userData.findAll();
    }
    public UserLogin getByLogin(String login) {
        return this.userData.getByLogin(login);
    }

    public List<UserLogin> findUser(String name){

        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserLogin> cr = cb.createQuery(UserLogin.class);
        Root<UserLogin> root = cr.from(UserLogin.class);

        cr.select(root).where(cb.like(root.get("name"), "%"+name+"%"));

        Query<UserLogin> query = session.createQuery(cr);


        return query.getResultList();
    }



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserLogin u = getByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), true, true, true, true, new HashSet<>());
    }



    public void registration(String name, String login,String password) throws Exception {
        if(login == null || password == null){
            throw new NullPointerException();
        }
        UserLogin user = userData.findByLogin(login);
        if(user != null){
            throw new Exception("user is created");
        }
        UserLogin newUser = new UserLogin(name,login,password);
        userData.save(newUser);
    }

//    public List<User> searchByLatitudeAndLongitude(float longitude, float Latitude){
//
//
//
//
//
//        return ;
//    }




}
