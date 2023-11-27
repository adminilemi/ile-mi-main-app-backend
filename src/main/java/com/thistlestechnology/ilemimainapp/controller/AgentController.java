package com.thistlestechnology.ilemimainapp.controller;

import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.LoginAgentRequest;
import com.thistlestechnology.ilemimainapp.service.AgentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController {
    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAgent(@RequestBody AgentRegisterRequest agentRegisterRequest){
        return new ResponseEntity<>(agentService.registerAgent(agentRegisterRequest), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAgentRequest loginAgentRequest){
        return new ResponseEntity<>(agentService.loginAgent(loginAgentRequest),HttpStatus.OK);
    }

}
