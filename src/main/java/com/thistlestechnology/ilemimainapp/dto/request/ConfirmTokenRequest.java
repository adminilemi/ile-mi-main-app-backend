package com.thistlestechnology.ilemimainapp.dto.request;

import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import lombok.Data;

@Data
public class ConfirmTokenRequest {
    private String email;
    private String token;
}
