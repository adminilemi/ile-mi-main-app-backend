package com.thistlestechnology.ilemimainapp.service;

import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getConfirmationToken(String token);
    void deleteConfirmationToken();
    void setExpiredAt(String token);
    ConfirmationToken findConfirmationTokenByToken(String token);
}
