package com.ussdwork.ussd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "leave")
@Data
@AllArgsConstructor
public class Leave {
    @Id
    private String id;
    private String name;
    private String phonenumber;
    private String groupId;

        // getters and setters
        public Leave()
        {
            super();
        }
    
        public Leave(String name, String phonenumber, String groupId) {
            super();
            this.name = name;
            this.phonenumber = phonenumber;
            this.groupId = groupId;
        }

        public String getName()
        {
            return name;
        }
    
            public String getPhoneNumber()
        {
            return phonenumber;
        }
    
            public String getGroupId()
        {
            return groupId;
        }
}
