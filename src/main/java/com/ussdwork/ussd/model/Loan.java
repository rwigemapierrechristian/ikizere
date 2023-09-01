package com.ussdwork.ussd.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "Loanstatement")
@Data
public class Loan {
@Id
    private String id;
    private String phonenumber;
    private String name;
    private double amount;
    private double interest;
    private String status;
    private LocalDate dueDate;


//getters and setters
 public Loan()
    {
        super();
    }

    public Loan(String phonenumber, String name, double amount, double interest,String status, LocalDate dueDate) {
        super();
        this.phonenumber = phonenumber;
        this.name = name;
        this.amount = amount;
        this.interest = interest;
        this.status = status;
        this.dueDate = dueDate;

    }

    public String getPhoneNumber()
    {
        return phonenumber;
    }

    public String getName()
    {
        return name;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount (double newAmount)
    {
        amount = newAmount;
    }

        public double getInterest()
    {
        return interest;
    }

    public void setInterest (double newInterest)
    {
        interest = newInterest;
    }
    public String getStatus() {

        return status;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
    }

}



