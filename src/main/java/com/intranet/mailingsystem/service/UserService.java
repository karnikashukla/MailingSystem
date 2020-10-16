package com.intranet.mailingsystem.service;

import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.repositories.AdminRepository;
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
        userRepository.insert(user);
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
}
