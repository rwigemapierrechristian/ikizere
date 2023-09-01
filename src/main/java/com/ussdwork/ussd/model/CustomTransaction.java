package com.ussdwork.ussd.model;
import java.time.LocalDateTime;


public class CustomTransaction {
    private int count;
    private LocalDateTime date;
    private String description;
    private double amount;

    public CustomTransaction(int count, LocalDateTime date, String description, double amount) {
        this.count = count;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    // Add getters and setters for the properties as needed
    public int getCount()
    {
        return count;
    }

        public LocalDateTime getDateTime()
    {
        return date;
    }

        public String getDescription()
    {
        return description;
    }

        public double getAmount()
    {
        return amount;
    }

        // Override the toString() method
        @Override
        public String toString() {
            return "CustomTransaction{" +
                    "count=" + count +
                    ", date=" + date +
                    ", description='" + description + '\'' +
                    ", amount=" + amount +
                    '}';
        }


}

