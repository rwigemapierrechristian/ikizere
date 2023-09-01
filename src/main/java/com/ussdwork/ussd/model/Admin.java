package com.ussdwork.ussd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin")
@Data
@AllArgsConstructor
public class Admin {
    @Id
    private String id;
    private String username;
    private String password;
    private String authToken;


    // getters and setters
    public Admin()
    {
        super();
    }

    public Admin(String authToken) {
    }

    public String getUserName()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail() {
        return username;
    }
}
