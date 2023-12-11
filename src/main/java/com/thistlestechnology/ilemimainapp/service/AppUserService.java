package com.thistlestechnology.ilemimainapp.service;

import com.thistlestechnology.ilemimainapp.dto.request.ResetPasswordRequest;
import com.thistlestechnology.ilemimainapp.dto.response.ForgotPasswordResponse;
import com.thistlestechnology.ilemimainapp.dto.response.PasswordResetResponse;
import com.thistlestechnology.ilemimainapp.model.AppUser;

import java.util.Optional;

public interface AppUserService {
    AppUser findByEmail(String email);
    Optional<AppUser> findById(String id);
    ForgotPasswordResponse sendVerificationMail(String email);
    PasswordResetResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
}
