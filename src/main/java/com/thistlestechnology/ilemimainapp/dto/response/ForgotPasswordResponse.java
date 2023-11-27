package com.thistlestechnology.ilemimainapp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgotPasswordResponse {
    private String token;
    private String message;
    private String userEmail;
}
