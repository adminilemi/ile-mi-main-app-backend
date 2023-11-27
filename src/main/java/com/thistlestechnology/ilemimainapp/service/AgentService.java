package com.thistlestechnology.ilemimainapp.service;

import com.github.fge.jsonpatch.JsonPatchException;
import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.LoginAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.request.UpdateAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.response.*;
import com.thistlestechnology.ilemimainapp.model.Agent;

public interface AgentService {
    AgentRegisterResponse registerAgent(AgentRegisterRequest agentRegisterRequest);

    boolean existsByEmail(String email);

    Agent findByEmail(String email);

    LoginAgentResponse loginAgent(LoginAgentRequest loginAgentRequest);

    AgentProfileResponse viewAgentProfileById(String id);

    ForgotPasswordResponse forgotPassword(String email);

    UpdateAgentResponse updateAgentProfile(UpdateAgentRequest updateAgentRequest)  ;

}
