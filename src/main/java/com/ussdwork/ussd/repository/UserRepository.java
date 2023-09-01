package com.ussdwork.ussd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ussdwork.ussd.model.*;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByPhoneNumber(String phoneNumber);
    void deleteByPhoneNumber(String phoneNumber);
}

