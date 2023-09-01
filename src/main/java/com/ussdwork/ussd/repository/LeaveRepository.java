package com.ussdwork.ussd.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ussdwork.ussd.model.Leave;

@Repository
public interface LeaveRepository extends MongoRepository<Leave,String>
 {
    Leave findByGroupId(String groupId);
    Leave findByPhonenumber(String phonenumber);
    Leave deleteByPhonenumber(String phonenumber);
    Leave deleteByGroupId(String groupId);
}
