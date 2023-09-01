package com.ussdwork.ussd.repository;

import com.ussdwork.ussd.model.Total;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TotalRepository extends MongoRepository<Total,String>
{
    Total findByName(String name);
}