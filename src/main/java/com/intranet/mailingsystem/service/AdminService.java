package com.intranet.mailingsystem.service;

import com.intranet.mailingsystem.models.Admin;
import com.intranet.mailingsystem.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    AdminRepository adminRepository;

    public List<Admin> adminLogin(String adminEmail){
        return adminRepository.findAll();
    }

    public void save(Admin admin){
        adminRepository.insert(admin);
    }


    public Admin getUserById(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, Admin.class);
    }
}
