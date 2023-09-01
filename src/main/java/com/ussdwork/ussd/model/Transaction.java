package com.ussdwork.ussd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "Transaction")
@Data
@AllArgsConstructor
public class Transaction {
      @Id
    private String id;
    private String phoneNumber;
    private String name;
    private double amount;
    private LocalDateTime transactionDateTime;
    private String description;


//getters and setters
 public Transaction()
    {
        super();
    }

    public Transaction(String phoneNumber, String name, double amount, LocalDateTime transactionDateTime,String description) {
        super();
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.amount = amount;
        this.transactionDateTime = transactionDateTime;
        this.description = description;

    }

    public String getNumber()
    {
        return phoneNumber;
    }

    public String getName()
    {
        return name;
    }

    public LocalDateTime getDateTime()
    {
        return transactionDateTime;
    }
    public String getDescription() {

        return description;
    }


}
