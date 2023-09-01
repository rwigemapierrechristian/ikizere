package com.ussdwork.ussd.model;

import org.springframework.data.annotation.Id;

public class Momo {
    @Id
    private String id;
    private String name;
    private String phonenumber;
    private double balance;


        public Momo()
    {
        super();
    }

    public Momo( String id,String name, String phonenumber, double balance) {
        super();
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.balance = balance;
    }

    public String getName()
    {
        return name;
    }
        public double getBalance()
    {
        return balance;
    }

        public void setBalance( double newBalance)
    {
        balance = newBalance;
    }
}

