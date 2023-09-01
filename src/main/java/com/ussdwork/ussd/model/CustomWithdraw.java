package com.ussdwork.ussd.model;

public class CustomWithdraw {
     private int count;
    private String name;
    private String phoneNumber;
    private String groupId;
    private double amount;

    public CustomWithdraw(int count, String name, String phoneNumber, String groupId, double amount) {
        this.count = count;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
        this.amount = amount;
    }

    // Add getters and setters for the properties as needed
    public int getCount()
    {
        return count;
    }

        public String getName()
    {
        return name;
    }

        public String getPhoneNumber()
    {
        return phoneNumber;
    }

        public String getGroupId()
    {
        return groupId;
    }

    public double getAmount()
    {
        return amount;
    }
        // Override the toString() method
        @Override
        public String toString() {
            return "CustomWithdraw{" + "count=" + count +", name=" + name +", phone Number= " + phoneNumber +", groupID=" + groupId +", amount = "+amount+'}';
        }
}
