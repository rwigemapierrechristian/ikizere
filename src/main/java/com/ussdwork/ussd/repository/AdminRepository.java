package com.ussdwork.ussd.repository;

import com.ussdwork.ussd.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdminRepository extends MongoRepository<Admin, String>
{
    Admin findByUsername(String username);
}
