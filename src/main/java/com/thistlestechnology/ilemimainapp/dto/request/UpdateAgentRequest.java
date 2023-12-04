package com.thistlestechnology.ilemimainapp.dto.request;

import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;

@Data
@Builder
public class UpdateAgentRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
//    private String email;
    private String officeAddress;
    private String homeAddress;
    private String NIN;
    private String state;
    private String localGovt;
    private String country;
}
