package com.intranet.mailingsystem.repositories;

import com.intranet.mailingsystem.models.Corporation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporationRepository extends MongoRepository<Corporation, String> {

}
