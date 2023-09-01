package com.ussdwork.ussd.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "pending-approval")
public class Pending {
    @Id
    private String id;
    private String name;
    private String phonenumber;
    private String groupId;

    public Pending()
    {
        super();
    }

    public Pending( String name, String phonenumber, String groupId)
    {
        super();
        this.name = name;
        this.phonenumber = phonenumber;
        this.groupId = groupId;


    }

            public String getGroupId()
        {
            return groupId;
        }

            public String getPhoneNumber()
        {
            return phonenumber;
        }

        public String getName()
        {
            return name;
        }


}

