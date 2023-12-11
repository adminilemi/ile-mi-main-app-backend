package com.thistlestechnology.ilemimainapp.controller;

import com.thistlestechnology.ilemimainapp.dto.request.ConfirmTokenRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ResetPasswordRequest;
import com.thistlestechnology.ilemimainapp.service.AgentService;
import com.thistlestechnology.ilemimainapp.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")

public class AppUserController {
    private final AgentService agentService;
    private final AppUserService appUserService;

    public AppUserController(AgentService agentService, AppUserService appUserService) {
        this.agentService = agentService;
        this.appUserService = appUserService;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody ConfirmTokenRequest confirmTokenRequest){
        return new ResponseEntity<>(agentService.verifyToken(confirmTokenRequest),HttpStatus.OK);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String email){
        return new ResponseEntity<>(appUserService.sendVerificationMail(email),HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        return new ResponseEntity<>(appUserService.resetPassword(resetPasswordRequest),HttpStatus.OK);
    }

}
