package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.model.SecuredUser;
import com.thistlestechnology.ilemimainapp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SecuredUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser foundUser = appUserRepository.findAppUserByEmail(email).orElse(null);
        return new SecuredUser(foundUser);
    }
}
