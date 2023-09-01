package com.ussdwork.ussd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "total")
@Data
@AllArgsConstructor

public class Total {
        @Id
    private String id;
    private String name;
    private double total;

     public Total()
    {
        super();
    }

    public Total(String name,double total) {
        super();
        this.name = name;
        this.total = total;

    }

    public String getName()
    {
        return name;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double newTotal)
    {
        total = newTotal;
    }
    
}


