package com.ussdwork.ussd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "withdraw")
@Data
@AllArgsConstructor
public class Withdraw {
    @Id
    private String id;
    private String name;
    private String phonenumber;
    private String groupId;
    private double amount;

        // getters and setters
        public Withdraw()
        {
            super();
        }
    
        public Withdraw(String name, String phonenumber, String groupId, double amount) {
            super();
            this.name = name;
            this.phonenumber = phonenumber;
            this.groupId = groupId;
            this.amount = amount;
        }

        // Add getters and setters for the properties as needed

    
            public String getName()
        {
            return name;
        }
    
            public String getPhoneNumber()
        {
            return phonenumber;
        }
    
            public String getGroupId()
        {
            return groupId;
        }
    
        public double getAmount()
        {
            return amount;
        }
}
