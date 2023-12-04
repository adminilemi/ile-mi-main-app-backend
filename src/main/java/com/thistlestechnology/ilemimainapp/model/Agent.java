package com.thistlestechnology.ilemimainapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "agents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Agent {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String officeAddress;
    private String homeAddress;
    private String NIN;
    private String state;
    private String localGovt;
    private String country;
    @DBRef
    private AppUser appUser;
}
