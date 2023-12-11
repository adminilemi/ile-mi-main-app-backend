package com.thistlestechnology.ilemimainapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String appUserEmail;
    private String receivedOtp;
    private String newPassword;
    private String confirmNewPassword;
}
