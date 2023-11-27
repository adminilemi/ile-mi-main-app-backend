package com.thistlestechnology.ilemimainapp.dto.request;

import lombok.Data;

@Data
public class AgentRegisterRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
}
