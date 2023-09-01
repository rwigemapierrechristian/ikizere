package com.ussdwork.ussd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ussdwork.ussd.model.Withdraw;

@Repository
public interface WithdrawRepository extends MongoRepository<Withdraw,String>
{
    Withdraw findByGroupId(String groupId);
    Withdraw findByPhonenumber(String phonenumber);
    Withdraw deleteByPhonenumber(String phonenumber);
    Withdraw deleteByGroupId(String groupId);
}
