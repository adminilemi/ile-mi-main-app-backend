package com.thistlestechnology.ilemimainapp.dto.request;

import lombok.Data;

@Data
public class LoginAgentRequest {
    private String email;
    private String password;
}
