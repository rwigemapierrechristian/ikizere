package com.ussdwork.ussd.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ussdwork.ussd.model.Transaction;

@Repository

public interface TransactionRepository extends MongoRepository<Transaction,String>
{
    Transaction findBynumber(String phoneNumber);
    List<Transaction> findAll();
    Transaction deleteByPhoneNumber(String phoneNumber);
}
