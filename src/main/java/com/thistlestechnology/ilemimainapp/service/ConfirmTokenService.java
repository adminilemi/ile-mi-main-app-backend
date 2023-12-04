package com.thistlestechnology.ilemimainapp.service;

import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;

public interface ConfirmTokenService {
    String generateAndSaveToken(String email);
    ConfirmationToken validateToken(String token);
//    void deleteConfirmationToken(ConfirmationToken confirmationToken);
//
//    void saveConfirmationToken(ConfirmationToken confirmationToken);
//    Optional<ConfirmationToken> getConfirmationToken(String token);
    void deleteConfirmationToken();
    void deleteConfirmationTokenOnVerification(ConfirmationToken token);
//    void setExpiredAt(String token);
//    ConfirmationToken findConfirmationTokenByToken(String token);
//    void setConfirmedAt(String token);
//

}
