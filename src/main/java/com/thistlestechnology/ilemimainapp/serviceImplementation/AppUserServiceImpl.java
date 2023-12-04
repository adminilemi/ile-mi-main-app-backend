package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.dto.request.EmailRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ResetPasswordRequest;
import com.thistlestechnology.ilemimainapp.dto.response.PasswordResetResponse;
import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.repository.AppUserRepository;
import com.thistlestechnology.ilemimainapp.service.AppUserService;
import com.thistlestechnology.ilemimainapp.service.ConfirmTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ConfirmTokenService confirmTokenService;

    @Override
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public Optional<AppUser> findById(String id) {
        return appUserRepository.findById(id);
    }

    @Override
    public String sendVerificationMail(String email) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));
        if (appUser.isVerified()){
//            var generatedToken = confirmTokenService.generateAndSaveToken(appUser);
            EmailRequest emailRequest = new EmailRequest();

        }
        return "a token has been sent,please verify your email";
    }

    @Override
    public PasswordResetResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        return null;
    }
}
