package com.ussdwork.ussd.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "groups")
@Data
@AllArgsConstructor
public class Groups {
    @Id
    private String id;
    private String name;
    private String number;
    private int members;
    private double totalSavings;




    // getters and setters
    public Groups()
    {
        super();
    }

    public Groups(String name, String number, int members, double totalSavings) {
        super();
        this.name = name;
        this.number = number;
        this.members = members;
        this.totalSavings = totalSavings;
    }

    public String getGroupName()
    {
        return name;
    }

        public String getGroupNumber()
    {
        return number;
    }

    public int getMembers()
    {
        return members;
    }

    public void setMembers(int newMember)
    {
        members = newMember;
    }

    public double getTotalSavings()
    {
        return totalSavings;
    }

    public void setTotalSavings(double newTotal)
    {
        totalSavings = newTotal;
    }

}
