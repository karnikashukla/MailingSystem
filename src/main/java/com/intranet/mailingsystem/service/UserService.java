package com.intranet.mailingsystem.service;

import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;


    public void save(User user){
        mongoTemplate.insert(user);
    }


    public User getUserByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getUserById(long userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    public void editUserProfile(User user, long userId){

        System.out.println(" User Id : " + userId);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        User user1 = getUserById(userId);
        System.out.println(" In UserService ::: Printing user1 ::: "+ user1.getId() + " + " + user1.getEmail());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setAlternateEmail(user.getAlternateEmail());
        user1.setPhone(user.getPhone());
        user1.setCorporationName(user.getCorporationName());
        mongoTemplate.save(user1);
    }

    public void editPassword(User user, String newPassword){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(user.getId()));
        user.setPassword(newPassword);
        mongoTemplate.save(user);
    }

    public List<User> getAllUsers(){
        return mongoTemplate.findAll(User.class);
    }

    public void deleteUser(long userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        mongoTemplate.remove(query, User.class);
    }

    public List<User> findUsersByCorporations(String corporationName){
        Query query = new Query();
        query.addCriteria(Criteria.where("corporationName").is(corporationName));
        return mongoTemplate.find(query, User.class);
    }

}
