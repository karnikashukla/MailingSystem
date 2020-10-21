package com.intranet.mailingsystem.service;

import com.intranet.mailingsystem.models.Admin;
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
    UserService userService;
    @Autowired
    AdminService adminService;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Mail> findAllMailsOfUserInbox(String userEmail){
        Query query = new Query();
        query.addCriteria(Criteria.where("toMail").is(userEmail));
        return mongoTemplate.find(query, Mail.class);
    }

    public List<Mail> findAllMailsOfUserSent(String userEmail){
        Query query = new Query();
        query.addCriteria(Criteria.where("fromMail").is(userEmail));
        return mongoTemplate.find(query, Mail.class);
    }

    public void save(Mail mail){
        mongoTemplate.insert(mail);
    }

    public boolean searchMail(String mail){
        if(adminService.getAdminByEmail(mail) != null){
            return true;
        }
        else {
            return false;
        }
    }

    public List<Mail> findAllMailsOfAdminInbox(String adminEmail){
        Query query = new Query();
        query.addCriteria(Criteria.where("toMail").is(adminEmail));
        return mongoTemplate.find(query,Mail.class);
    }

    public List<Mail> findAllMailsOfAdminSent(String adminEmail){
        Query query = new Query();
        query.addCriteria(Criteria.where("fromMail").is(adminEmail));
        return mongoTemplate.find(query,Mail.class);
    }

    public Mail findMailById(long mailId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(mailId));
        return mongoTemplate.findOne(query,Mail.class);
    }
}
