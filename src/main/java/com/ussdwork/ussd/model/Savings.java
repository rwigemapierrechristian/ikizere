package com.ussdwork.ussd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "savings")
@Data
@AllArgsConstructor
public class Savings {
    @Id
    private String id;
    private String phoneNumber;
    private String name;
    private String month;
    private double amount;


//getters and setters
 public Savings()
    {
        super();
    }

    public Savings(String phoneNumber, String name, String month, double amount) {
        super();
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.month = month;
        this.amount = amount;

    }

    public String getNumber()
    {
        return phoneNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getMonth()
    {
        return month;
    }

    public double getAmount() {

        return amount;
    }

    public void setAmount(double newAmount) {
        amount = newAmount;
    }

}
