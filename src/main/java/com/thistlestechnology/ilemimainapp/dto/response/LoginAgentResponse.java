package com.thistlestechnology.ilemimainapp.dto.response;

import lombok.*;

@Builder
@Getter
public class LoginAgentResponse {
    private String message;
    private String bearerToken;
}
