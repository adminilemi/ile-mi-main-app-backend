package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.dto.request.EmailRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ResetPasswordRequest;
import com.thistlestechnology.ilemimainapp.dto.response.ForgotPasswordResponse;
import com.thistlestechnology.ilemimainapp.dto.response.PasswordResetResponse;
import com.thistlestechnology.ilemimainapp.exception.PasswordMismatchException;
import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import com.thistlestechnology.ilemimainapp.repository.AppUserRepository;
import com.thistlestechnology.ilemimainapp.service.AppUserService;
import com.thistlestechnology.ilemimainapp.service.ConfirmTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.thistlestechnology.ilemimainapp.utils.EmailUtils.buildForgotPasswordEmail;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ConfirmTokenService confirmTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public Optional<AppUser> findById(String id) {
        return appUserRepository.findById(id);
    }

    @Override
    public ForgotPasswordResponse sendVerificationMail(String email) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));
        if (appUser.isVerified()){
            var generatedToken = confirmTokenService.generateAndSaveToken(appUser.getEmail());
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setMessage(buildForgotPasswordEmail(appUser.getUsername(), generatedToken));
            emailRequest.setReceiver(email);
            return ForgotPasswordResponse.builder()
                    .token(generatedToken)
                    .message("an email has been sent,go to your email and verify")
                    .build();
        }
        return ForgotPasswordResponse.builder()
                .message("email not sent, user might not be registered")
                .build();
    }
    private String verifyToken(String token){
        log.info("token is logged" + token);
        ConfirmationToken foundToken = confirmTokenService.getConfirmationToken(token);
            if (foundToken.getExpiredAt().isBefore(LocalDateTime.now()))
                throw new IllegalStateException("token is expired");
            if (foundToken.getConfirmAt() != null) throw new IllegalStateException("token has already been confirmed");
            return foundToken.getUserEmail();
    }

    @Override
    public PasswordResetResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
       String email = verifyToken(resetPasswordRequest.getReceivedOtp());
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword()))
            throw new PasswordMismatchException("Password does match");
        AppUser foundUser = appUserRepository.findAppUserByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        foundUser.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        appUserRepository.save(foundUser);
        return PasswordResetResponse.builder()
                .message("password reset successful")
                .build();
    }
}
