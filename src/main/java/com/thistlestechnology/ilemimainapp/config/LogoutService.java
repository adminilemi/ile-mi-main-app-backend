package com.thistlestechnology.ilemimainapp.config;

import com.thistlestechnology.ilemimainapp.repository.ConfirmationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        String jwt = authHeader.substring(7);
        var storedToken = confirmationTokenRepository.findByAccessToken(jwt)
                .orElse(null);
        if(storedToken != null){
            AppUser appUser = storedToken.getAppUser();
            var tokens = tokenRepository.findAllByAppUser_Id(appUser.getId());
            tokenRepository.deleteAll(tokens);
            SecurityContextHolder.clearContext();
        }
    }
}
