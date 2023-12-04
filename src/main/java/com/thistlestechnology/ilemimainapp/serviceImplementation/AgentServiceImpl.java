package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.config.JwtService;
import com.thistlestechnology.ilemimainapp.dto.request.*;
import com.thistlestechnology.ilemimainapp.dto.response.*;
import com.thistlestechnology.ilemimainapp.exception.AgentExistsException;
import com.thistlestechnology.ilemimainapp.exception.PasswordMismatchException;
import com.thistlestechnology.ilemimainapp.model.Agent;
import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import com.thistlestechnology.ilemimainapp.model.enums.Role;
import com.thistlestechnology.ilemimainapp.repository.AgentRepository;
import com.thistlestechnology.ilemimainapp.repository.AppUserRepository;
import com.thistlestechnology.ilemimainapp.service.AgentService;
import com.thistlestechnology.ilemimainapp.service.AppUserService;
import com.thistlestechnology.ilemimainapp.service.ConfirmTokenService;
import com.thistlestechnology.ilemimainapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.thistlestechnology.ilemimainapp.utils.EmailUtils.buildEmail;
import static com.thistlestechnology.ilemimainapp.utils.EmailUtils.buildForgotPasswordEmail;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final AppUserRepository appUserRepository;

    private AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final ConfirmTokenService confirmTokenService;
    private final AuthenticationManager authenticationManager;
    private final SecuredUserService securedUserService;
    private final JwtService jwtService;
    @Override
    public AgentRegisterResponse registerAgent(AgentRegisterRequest agentRegisterRequest) {
        if(appUserRepository.findByEmail(agentRegisterRequest.getEmail()) != null)
            throw new AgentExistsException("Agent already exist, please login");
        String hashedPassword = passwordEncoder.encode(agentRegisterRequest.getPassword());
        AppUser appUser = modelMapper.map(agentRegisterRequest, AppUser.class);
        appUser.setPassword(hashedPassword);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.AGENT);
        appUser.setUserRoles(userRoles);
        AppUser savedAppUser = appUserRepository.save(appUser);
        Agent savedAgent = creatAgent(agentRegisterRequest, savedAppUser);

        String token = confirmTokenService.generateAndSaveToken(savedAppUser.getEmail());
        sendConfirmationToken(savedAgent.getFirstName(), savedAppUser.getEmail(), token);
        return AgentRegisterResponse.builder()
                .agentId(savedAgent.getId())
                .message("agent has been registered successfully,go to your email and confirm email")
                .build();
    }

    private Agent creatAgent(AgentRegisterRequest request, AppUser appUser){
        Agent agent = modelMapper.map(request, Agent.class);
        agent.setAppUser(appUser);
        return agentRepository.save(agent);
    }

    private void sendConfirmationToken(String firstName, String email, String token) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setMessage(buildEmail(firstName, token));
        emailRequest.setReceiver(email);
    }

//    private boolean agentExistsByEmail(String email){
//        List<Agent> agents = agentRepository.findAll();
//        for(Agent agent : agents){
//            if(agent.getAppUser().getEmail().equals(email))
//                return true;
//        }
//        return false;
//    }

    private Agent getAgentByEmail(String email){
        List<Agent> agents = agentRepository.findAll();
        for(Agent agent : agents){
            if(agent.getAppUser().getEmail().equals(email))
                return agent;
        }
        throw new IllegalStateException("Agent not found");
    }

    @Override
    public ConfirmTokenResponse verifyToken(ConfirmTokenRequest confirmTokenRequest) {
        AppUser appUser = appUserRepository.findByEmail(confirmTokenRequest.getEmail());
        ConfirmationToken confirmationToken =
                confirmTokenService.validateToken(confirmTokenRequest.getToken());
        if(confirmationToken.getUserEmail().equals(confirmTokenRequest.getEmail())){
            if (appUser.isVerified()) {
                throw new IllegalStateException("Agent is already verified");
            } else {
                appUser.setVerified(true);
                appUserRepository.save(appUser);
                confirmTokenService.deleteConfirmationTokenOnVerification(confirmationToken);

            }
        }
        return ConfirmTokenResponse.builder()
                .message("Token verified successfully")
                .build();
    }

    @Override
    public  boolean existsByEmail(String email){
//        return agentRepository.existsByAppUser_Email(email);
        return false;
    }



    private Agent findAgentById(String id) {
        return agentRepository
                .findById(id).orElseThrow(()-> new AgentExistsException("Agent doesn't exist. Plz register"));
    }


    @Override
    public LoginAgentResponse loginAgent(LoginAgentRequest loginAgentRequest) {;
       AppUser foundAppUser = appUserRepository.findByEmail(loginAgentRequest.getEmail());
        if (!passwordEncoder.matches(loginAgentRequest.getPassword(), foundAppUser.getPassword())){
            throw new PasswordMismatchException("incorrect password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginAgentRequest.getEmail(),
                        loginAgentRequest.getPassword()
                )
        );
        UserDetails userDetails = securedUserService.loadUserByUsername(loginAgentRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        return LoginAgentResponse.builder()
             .message("Bearer "+token)
                .build();

    }



    @Override
    public AgentProfileResponse viewAgentProfileById(String id) {
        Agent agent = findAgentById(id);
        return  modelMapper.map(agent, AgentProfileResponse.class);
    }


    @Override
    public UpdateAgentResponse updateAgentProfile(UpdateAgentRequest updateAgentRequest) {
        Agent agent = findAgentById(updateAgentRequest.getId());
        mapRequestToAgent(updateAgentRequest, agent);
        Agent updatedAgent = agentRepository.save(agent);
        return modelMapper.map(updatedAgent, UpdateAgentResponse.class);
    }

    private void mapRequestToAgent(UpdateAgentRequest updateAgentRequest, Agent agent) {
        agent.setFirstName(updateAgentRequest.getFirstName());
        agent.setLastName(updateAgentRequest.getLastName());
        agent.setPhoneNumber(updateAgentRequest.getPhoneNumber());
        agent.setHomeAddress(updateAgentRequest.getHomeAddress());
        agent.setNIN(updateAgentRequest.getNIN());
        agent.setLocalGovt(updateAgentRequest.getLocalGovt());
        agent.setOfficeAddress(updateAgentRequest.getOfficeAddress());
        agent.setState(updateAgentRequest.getState());
        agent.setCountry(updateAgentRequest.getCountry());
    }




    @Override
    public boolean isVerified(String email) {
        return false;
//       var foundAgent =findByEmail(email);
//        return foundAgent != null && foundAgent.getAppUser().isVerified();
    }

//    public UpdateAgentResponse simpleUpdateAgentProfile(UpdateAgentRequest updateAgentRequest)  {
//        Agent agent = modelMapper.map()
//        return modelMapper.map(agent, UpdateAgentResponse.class);
//
//    }









//    public UpdateAgentResponse  jsonUpdateAgentProfile(UpdateAgentRequest updateAgentRequest) throws JsonPatchException {
//
//        Agent agent = findByEmail(updateAgentRequest.getEmail());
//
//        JsonPatch updatePatch = buildUpdatePatch(updateAgentRequest);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode agentNode = objectMapper.convertValue(agent, JsonNode.class);
//        JsonNode updatedNode = updatePatch.apply(agentNode);
//        Agent updatedAgent = objectMapper.convertValue(updatedNode, Agent.class);
//
//        agentRepository.save(updatedAgent);
//        modelMapper.map(updateAgentRequest, agent);
//
//
//        JsonPatch updatedPatch = buildUpdatePatch(updateAgentRequest);
//        return applyPatch(updatedPatch, agent);
    }
//    private JsonPatch buildUpdatePatch(UpdateAgentRequest updateAgentRequest) {
//        Field[] fields = updateAgentRequest.getClass().getDeclaredFields();
//        List<ReplaceOperation> operations = Arrays.stream(fields)
//                .filter(field -> validateField(updateAgentRequest, field))
//                .map(field -> buildReplaceOperation(updateAgentRequest, field))
//                .toList();
//        List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
//        return new JsonPatch(patchOperations);
//    }
//    private ReplaceOperation buildReplaceOperation(UpdateAgentRequest updateAgentRequest, Field field) {
//        field.setAccessible(true);
//        try {
//            String path =
////                    JSON_PATCH_PATH_PREFIX+
//                    "/"+ field.getName();
//            JsonPointer pointer = new JsonPointer(path);
//            var value = field.get(updateAgentRequest);
//            TextNode node = new TextNode(value.toString());
//            return new ReplaceOperation(pointer, node);
//        } catch (Exception exception) {
//            throw new RuntimeException (exception.getMessage());
//        }
//    }
//    private boolean validateField(UpdateAgentRequest updateAgentRequest, Field field) {
//        field.setAccessible(true);
//
//        try {
//            return field.get(updateAgentRequest) != null;
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException (e.getMessage());
//        }
//    }
//    private UpdateAgentResponse applyPatch(JsonPatch updatePatch, Agent agent) throws JsonPatchException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        //1. Convert user to JsonNode
//        JsonNode userNode = objectMapper.convertValue(agent, JsonNode.class);
//
//        //2. Apply patch to JsonNode from step 1
//        JsonNode updatedNode = updatePatch.apply(userNode);
//        //3. Convert updatedNode to user
//        agent = objectMapper.convertValue(updatedNode, Agent.class);
//
//        String email = agent.getEmail().toLowerCase();
//        agent.setEmail(email);
//        //4. Save updatedUser from step 3 in the DB
//        var savedAgent= agentRepository.save(agent);
//        UpdateAgentResponse updateAgentResponse = new UpdateAgentResponse();
////        updateAgentResponse.setMessage(PROFILE_UPDATE_SUCCESSFUL.getMessage());
//        BeanUtils.copyProperties(savedAgent, updateAgentResponse);
//        return updateAgentResponse;
//
//    }
//}
