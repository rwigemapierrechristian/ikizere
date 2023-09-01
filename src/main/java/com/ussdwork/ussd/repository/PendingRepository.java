package com.ussdwork.ussd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ussdwork.ussd.model.Pending;

@Repository
public interface PendingRepository extends MongoRepository<Pending, String>{
    Pending findByGroupId(String groupId);
    Pending findByPhonenumber(String phonenumber);
    Pending deleteByPhonenumber(String phonenumber);
    Pending deleteByGroupId(String groupId);
}

