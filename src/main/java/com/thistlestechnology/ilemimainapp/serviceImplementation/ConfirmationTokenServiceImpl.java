package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import com.thistlestechnology.ilemimainapp.repository.ConfirmationTokenRepository;
import com.thistlestechnology.ilemimainapp.service.ConfirmTokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> getConfirmationToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void deleteConfirmationToken() {
        confirmationTokenRepository.deleteTokenByExpiredAtBefore(LocalDateTime.now());
    }

    @Override
    public void setExpiredAt(String token) {
        confirmationTokenRepository.setExpiredAt(LocalDateTime.now(),token);
    }

    @Override
    public ConfirmationToken findConfirmationTokenByToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(()->new RuntimeException("token not found"));
    }

}
