package com.intranet.mailingsystem.service;

import com.intranet.mailingsystem.models.Corporation;
import com.intranet.mailingsystem.repositories.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorporationService {
    @Autowired
    CorporationRepository corporationRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Corporation corporation){
        mongoTemplate.insert(corporation);
    }

    public Corporation findByCorporationName(String corporationName){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(corporationName));
        return mongoTemplate.findOne(query, Corporation.class);
    }

    public List<Corporation> findAllCorporation(){
        return mongoTemplate.findAll(Corporation.class);
    }

    public void deleteCorporation(long corporationId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(corporationId));
        mongoTemplate.remove(query, Corporation.class);
    }
}
