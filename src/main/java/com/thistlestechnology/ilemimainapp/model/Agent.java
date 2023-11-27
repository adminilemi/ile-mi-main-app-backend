package com.thistlestechnology.ilemimainapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "agents")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Agent {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String officeAddress;
    private String homeAddress;
    private String NIN;
    private String state;
    private String localGovt;
    private String country;
    @DBRef
    private ConfirmationToken confirmationToken;
    private boolean isEnabled = false;

}
