package com.intranet.mailingsystem.repositories;

import com.intranet.mailingsystem.models.LoginModel;
import com.intranet.mailingsystem.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findByEmail(String email);
}
