package com.ussdwork.ussd.repository;

import com.ussdwork.ussd.model.Groups;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface GroupsRepository extends MongoRepository<Groups, String>
{
    Groups findByNumber(String number);

    Groups deleteByNumber(String number);
}
