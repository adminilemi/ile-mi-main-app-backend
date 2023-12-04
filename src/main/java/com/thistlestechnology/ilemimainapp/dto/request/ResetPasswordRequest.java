package com.thistlestechnology.ilemimainapp.dto.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    private String newPassword;
    private String confirmNewPassword;
}
