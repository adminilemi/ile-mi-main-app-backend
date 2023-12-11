package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import com.thistlestechnology.ilemimainapp.repository.AppUserRepository;
import com.thistlestechnology.ilemimainapp.repository.ConfirmationTokenRepository;
import com.thistlestechnology.ilemimainapp.service.ConfirmTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public String generateAndSaveToken(String email) {
        ConfirmationToken existingConfirmationToken =
                confirmationTokenRepository.findConfirmationTokenByUserEmail(email);
        if(existingConfirmationToken != null)
            confirmationTokenRepository.delete(existingConfirmationToken);
        String token = generateToken();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .userEmail(email)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(10L))
                .build();
        confirmationTokenRepository.save(confirmationToken);
        return token;
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(secureRandom.nextInt(1000, 10000));
    }

    @Override
    public ConfirmationToken validateToken( String token) {
        ConfirmationToken confirmationToken =
                confirmationTokenRepository.findConfirmationTokenByToken(token);
     if(confirmationToken == null)
         throw new IllegalStateException("Invalid token received");
     else if(confirmationToken.getExpiredAt().isBefore(LocalDateTime.now()))
         throw new IllegalStateException("Token has expired");
     return confirmationToken;
    }

    @Override
    public ConfirmationToken getConfirmationToken(String token) {
        return confirmationTokenRepository.findConfirmationTokenByToken(token);
    }

    @Override
    public void deleteConfirmationToken() {
        confirmationTokenRepository.deleteConfirmationTokensByExpiredAtBefore(LocalDateTime.now());
    }

    @Override
    public void deleteConfirmationTokenOnVerification(ConfirmationToken token) {
        AppUser foundUser = appUserRepository.findByEmail(token.getUserEmail());
        if (foundUser.isVerified()){
            confirmationTokenRepository.delete(token);
        }

    }
}
