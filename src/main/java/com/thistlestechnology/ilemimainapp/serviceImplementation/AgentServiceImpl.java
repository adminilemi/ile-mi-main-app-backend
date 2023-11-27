package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.EmailRequest;
import com.thistlestechnology.ilemimainapp.dto.request.LoginAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.request.UpdateAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.response.*;
import com.thistlestechnology.ilemimainapp.exception.AgentExistsException;
import com.thistlestechnology.ilemimainapp.model.Agent;
import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import com.thistlestechnology.ilemimainapp.repository.AgentRepository;
import com.thistlestechnology.ilemimainapp.service.AgentService;
import com.thistlestechnology.ilemimainapp.service.ConfirmTokenService;
import com.thistlestechnology.ilemimainapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.thistlestechnology.ilemimainapp.utils.EmailUtils.buildEmail;
import static com.thistlestechnology.ilemimainapp.utils.EmailUtils.buildForgotPasswordEmail;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;

    private final EmailService emailService;
    private final ConfirmTokenService confirmTokenService;
    @Override
    public AgentRegisterResponse registerAgent(AgentRegisterRequest agentRegisterRequest) {
        boolean foundAgent = existsByEmail(agentRegisterRequest.getEmail());
        if (foundAgent){
            throw new AgentExistsException("agent is already registered,please login");
        }

        Agent agent = modelMapper.map(agentRegisterRequest, Agent.class);

        agentRepository.save(agent);
        String token = generateOtp();
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setMessage(buildEmail(agentRegisterRequest.getFirstName(),token));
        emailRequest.setReceiver(agentRegisterRequest.getEmail());
        log.info(token);
        try{
            emailService.sendOTP(emailRequest);
        }catch (Exception e){
            log.error("email is having connection issues ");
        }
        ConfirmationToken confirmationToken =
                new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(10),agent);
        confirmationToken.setConfirmAt(LocalDateTime.now());
        confirmTokenService.getConfirmationToken(token);
        agentRepository.save(agent);

        return AgentRegisterResponse.builder()
                .id(agent.getId())
                .message("agent has been registered successfully,go to your email and confirm email")
                .build();
    }
    @Override
    public  boolean existsByEmail(String email){
        return agentRepository.existsByEmail(email);
    }
    private Agent findAgentById(String id) {
        return agentRepository
                .findById(id).orElseThrow(()-> new AgentExistsException("Agent doesn't exist. Plz register"));
    }

    @Override
    public Agent findByEmail(String email){
        return agentRepository
                .findByEmail(email).orElseThrow(()-> new AgentExistsException("Agent with this email doesn't exist. Plz register"));
    }
    @Override
    public LoginAgentResponse loginAgent(LoginAgentRequest loginAgentRequest) {
        String agentEmail = loginAgentRequest.getEmail();
        boolean foundAgent = existsByEmail(agentEmail);
        LoginAgentResponse loginAgentResponse = new LoginAgentResponse();;
        if (!foundAgent){
            throw new AgentExistsException("not found");
        }
        Agent agent = findByEmail(agentEmail);
        boolean passwordMatches = agent.getPassword().equals(loginAgentRequest.getPassword());

        if(passwordMatches){
            modelMapper.map(loginAgentRequest, Agent.class);
            loginAgentResponse.setMessage("login successful");
            return loginAgentResponse;
        }
        loginAgentResponse.setMessage("login failed,password or email mismatch");
        return loginAgentResponse;
    }

    @Override
    public AgentProfileResponse viewAgentProfileById(String id) {
        Agent agent = findAgentById(id);
        AgentProfileResponse agentProfileResponse = modelMapper.map(agent, AgentProfileResponse.class);

        return agentProfileResponse;
    }

    @Override
    public ForgotPasswordResponse forgotPassword(String email) {
        var foundAgent = findByEmail(email);
        if (Objects.equals(foundAgent.getEmail(), email)){
            String token = generateOtp();
            ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(5),foundAgent);
            confirmTokenService.saveConfirmationToken(confirmationToken);
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setMessage(buildForgotPasswordEmail(foundAgent.getLastName(),token));
            emailRequest.setReceiver(foundAgent.getEmail());
            emailService.sendOTP(emailRequest);
            return ForgotPasswordResponse.builder()
                    .message("otp sent to your email enter it to verify your email")
                    .token(token)
                    .build();
        }
        return ForgotPasswordResponse.builder()
                .message("failed try again")
                .build();

    }

    private String generateOtp(){
        StringBuilder generatedOtp = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        int otp =1000 + secureRandom.nextInt(9000);
        generatedOtp.append(otp);
        return generatedOtp.toString();
    }

    @Override
    public UpdateAgentResponse updateAgentProfile(UpdateAgentRequest updateAgentRequest) {
        return simpleUpdateAgentProfile(updateAgentRequest);
//        return jsonUpdateAgentProfile(updateAgentRequest);
    }

    public UpdateAgentResponse simpleUpdateAgentProfile(UpdateAgentRequest updateAgentRequest)  {

        Agent agent = findAgentById(updateAgentRequest.getEmail());

        if (updateAgentRequest.getFirstName() != null )
            agent.setFirstName(updateAgentRequest.getFirstName());

        if (updateAgentRequest.getLastName() != null )
            agent.setLastName(updateAgentRequest.getLastName());

        if (updateAgentRequest.getPhoneNumber() != null )
            agent.setPhoneNumber(updateAgentRequest.getPhoneNumber());

        Agent updatedAgent = agentRepository.save(agent);

        return modelMapper.map(agent, UpdateAgentResponse.class);

    }









    public UpdateAgentResponse  jsonUpdateAgentProfile(UpdateAgentRequest updateAgentRequest) throws JsonPatchException {

        Agent agent = findByEmail(updateAgentRequest.getEmail());

        JsonPatch updatePatch = buildUpdatePatch(updateAgentRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode agentNode = objectMapper.convertValue(agent, JsonNode.class);
        JsonNode updatedNode = updatePatch.apply(agentNode);
        Agent updatedAgent = objectMapper.convertValue(updatedNode, Agent.class);

        agentRepository.save(updatedAgent);
        modelMapper.map(updateAgentRequest, agent);


        JsonPatch updatedPatch = buildUpdatePatch(updateAgentRequest);
        return applyPatch(updatedPatch, agent);
    }
    private JsonPatch buildUpdatePatch(UpdateAgentRequest updateAgentRequest) {
        Field[] fields = updateAgentRequest.getClass().getDeclaredFields();
        List<ReplaceOperation> operations = Arrays.stream(fields)
                .filter(field -> validateField(updateAgentRequest, field))
                .map(field -> buildReplaceOperation(updateAgentRequest, field))
                .toList();
        List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
        return new JsonPatch(patchOperations);
    }
    private ReplaceOperation buildReplaceOperation(UpdateAgentRequest updateAgentRequest, Field field) {
        field.setAccessible(true);
        try {
            String path =
//                    JSON_PATCH_PATH_PREFIX+
                    "/"+ field.getName();
            JsonPointer pointer = new JsonPointer(path);
            var value = field.get(updateAgentRequest);
            TextNode node = new TextNode(value.toString());
            return new ReplaceOperation(pointer, node);
        } catch (Exception exception) {
            throw new RuntimeException (exception.getMessage());
        }
    }
    private boolean validateField(UpdateAgentRequest updateAgentRequest, Field field) {
        field.setAccessible(true);

        try {
            return field.get(updateAgentRequest) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException (e.getMessage());
        }
    }
    private UpdateAgentResponse applyPatch(JsonPatch updatePatch, Agent agent) throws JsonPatchException {
        ObjectMapper objectMapper = new ObjectMapper();
        //1. Convert user to JsonNode
        JsonNode userNode = objectMapper.convertValue(agent, JsonNode.class);

        //2. Apply patch to JsonNode from step 1
        JsonNode updatedNode = updatePatch.apply(userNode);
        //3. Convert updatedNode to user
        agent = objectMapper.convertValue(updatedNode, Agent.class);

        String email = agent.getEmail().toLowerCase();
        agent.setEmail(email);
        //4. Save updatedUser from step 3 in the DB
        var savedAgent= agentRepository.save(agent);
        UpdateAgentResponse updateAgentResponse = new UpdateAgentResponse();
//        updateAgentResponse.setMessage(PROFILE_UPDATE_SUCCESSFUL.getMessage());
        BeanUtils.copyProperties(savedAgent, updateAgentResponse);
        return updateAgentResponse;

    }
}
