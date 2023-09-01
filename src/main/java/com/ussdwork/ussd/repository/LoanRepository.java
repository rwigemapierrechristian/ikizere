package com.ussdwork.ussd.repository;

import com.ussdwork.ussd.model.Loan;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
    Loan findByPhonenumber(String phoneNumber);
    Loan deleteByPhoneNumber(String phoneNumber);
}
