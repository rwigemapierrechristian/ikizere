package com.ussdwork.ussd.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ussdwork.ussd.model.Savings;
import java.util.List;


@Repository
public interface SavingsRepository extends MongoRepository<Savings, String> {
    Savings deleteByPhoneNumber(String phoneNumber);
    Savings  findByPhoneNumber(String phoneNumber);
}
