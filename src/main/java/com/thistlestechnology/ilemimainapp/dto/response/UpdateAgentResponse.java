package com.thistlestechnology.ilemimainapp.dto.response;

import lombok.Data;

@Data
public class UpdateAgentResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private String officeAddress;
    private String homeAddress;
    private String NIN;
    private String state;
    private String localGovt;
    private String country;
}
