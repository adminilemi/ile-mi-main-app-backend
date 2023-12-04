package com.thistlestechnology.ilemimainapp.service;

import com.github.fge.jsonpatch.JsonPatchException;
import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ConfirmTokenRequest;
import com.thistlestechnology.ilemimainapp.dto.request.LoginAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.request.UpdateAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.response.*;
import com.thistlestechnology.ilemimainapp.model.Agent;

public interface AgentService {
    AgentRegisterResponse registerAgent(AgentRegisterRequest agentRegisterRequest);
    ConfirmTokenResponse verifyToken(ConfirmTokenRequest confirmTokenRequest);

    boolean existsByEmail(String email);

//    Agent findByEmail(String email);

    LoginAgentResponse loginAgent(LoginAgentRequest loginAgentRequest);

    AgentProfileResponse viewAgentProfileById(String id);



    UpdateAgentResponse updateAgentProfile(UpdateAgentRequest updateAgentRequest)  ;

    boolean isVerified(String email);

}
