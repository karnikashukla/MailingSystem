package com.intranet.mailingsystem.service;

import com.intranet.mailingsystem.models.Mail;
import com.intranet.mailingsystem.models.User;
import com.intranet.mailingsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Mail> findAllMailsOfUser(String userEmail){
        Query query = new Query();
        query.addCriteria(Criteria.where("toMail").is(userEmail));
        return mongoTemplate.find(query, Mail.class);
    }

    public void save(Mail mail){
        mongoTemplate.insert(mail);
    }
}
