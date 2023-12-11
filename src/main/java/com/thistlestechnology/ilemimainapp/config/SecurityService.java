package com.thistlestechnology.ilemimainapp.config;

import com.thistlestechnology.ilemimainapp.utils.ProjectAuthenticationEntryPoint;
import com.thistlestechnology.ilemimainapp.utils.WhiteList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private  final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ProjectAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                WhiteList.freeAccess())
                        .permitAll()
                        .requestMatchers(WhiteList.swagger())
                        .permitAll()
                        .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("api/v1/agent/**").hasAuthority("AGENT")
                        .requestMatchers("/api/v1/agent/**").permitAll()
                        .requestMatchers("/api/v1/user/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }
}
