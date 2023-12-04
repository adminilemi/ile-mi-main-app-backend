package com.thistlestechnology.ilemimainapp.controller;

import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ConfirmTokenRequest;
import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import com.thistlestechnology.ilemimainapp.service.AgentService;
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

    public AppUserController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody ConfirmTokenRequest confirmTokenRequest){
        System.out.println("i got called in the controller");
        return new ResponseEntity<>(agentService.verifyToken(confirmTokenRequest),HttpStatus.OK);
    }
}
