package com.ussdwork.ussd.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "userInfo")
@Data
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String pin;
    private double depositAmount;
    private String savingsGroup;
    private String groupId;
    private String membershipStatus;
    private String membershipId;
    private String language;



    // getters and setters
    public User()
    {
        super();
    }

    public User(String name, String phoneNumber, String pin, double depositAmount, String savingsGroup,String groupId,String membershipStatus,String membershipId, String language) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.depositAmount = depositAmount;
        this.savingsGroup = savingsGroup;
        this.groupId = groupId;
        this.membershipStatus = membershipStatus;
        this.membershipId = membershipId;
        this.language = language;
    }

    public double getDepositAmount()
    {
        return depositAmount;
    }


    public String getPin()
    {
        return pin;
    }

    public String getName() {

        return name;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setDepositAmount (double newDepositAmount)
    {
        depositAmount = newDepositAmount;
    }

    public void setLanguage(String newLanguage)
    {
        language = newLanguage;
    }

    public void setNewPin(String newPin) {
        pin = newPin;
    }

    public String getSavingsGroup()
    {
        return savingsGroup;
    }

    public void setSavingsGroup(String newGroup)
    {
        savingsGroup = newGroup;
    }

    public String getMembershipStatus()
    {
        return membershipStatus;
    }

    public void setMembershipStatus(String newMembershipStat)
    {
        membershipStatus = newMembershipStat;
    }

        public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String newGroupId)
    {
        groupId = newGroupId;
    }

        public String getMembershipId()
    {
        return membershipId;
    }

    public void setMembershipId(String newMembershipId)
    {
        membershipId = newMembershipId;
    }

}
