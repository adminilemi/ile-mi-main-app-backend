package com.thistlestechnology.ilemimainapp.utils;

import lombok.Getter;

@Getter
public class WhiteList {
    public static String[] freeAccess() {
        return new String[]{
                "/api/v1/agent/register",
                "/api/v1/user/forgot-password",
                "/api/v1/user/reset-password",
                "/user/verify",
                "/user/login",
                "/user/resend_verification_mail",
        };
    }

    public static String[] swagger() {
        return new String[]{
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "webjars/**",
                "/swagger-ui.html"
        };
    }
}
