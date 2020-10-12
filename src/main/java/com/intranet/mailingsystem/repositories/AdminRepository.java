package com.intranet.mailingsystem.repositories;

import com.intranet.mailingsystem.models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin,String> {

    Optional<Admin> findById(String Id);


}
