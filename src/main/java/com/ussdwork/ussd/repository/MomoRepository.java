package com.ussdwork.ussd.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ussdwork.ussd.model.Momo;

@Repository
public interface MomoRepository extends MongoRepository<Momo, String>{
    Momo findByPhonenumber(String phoneNumber);
}
