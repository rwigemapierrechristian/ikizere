package com.ussdwork.ussd.model;

public class CustomLeave {
     private int count;
    private String name;
    private String phoneNumber;
    private String groupId;

    public CustomLeave(int count, String name, String phoneNumber, String groupId) {
        this.count = count;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
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

        // Override the toString() method
        @Override
        public String toString() {
            return "CustomLeave{" +
                    "count=" + count +
                    ", name=" + name +
                    ", phone Number='" + phoneNumber + '\'' +
                    ", groupID=" + groupId +
                    '}';
        }
}
