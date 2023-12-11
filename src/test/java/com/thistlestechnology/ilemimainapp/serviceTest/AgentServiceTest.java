package com.thistlestechnology.ilemimainapp.serviceTest;

import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ConfirmTokenRequest;
import com.thistlestechnology.ilemimainapp.dto.request.LoginAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.request.UpdateAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.response.AgentProfileResponse;
import com.thistlestechnology.ilemimainapp.dto.response.AgentRegisterResponse;
import com.thistlestechnology.ilemimainapp.dto.response.LoginAgentResponse;
import com.thistlestechnology.ilemimainapp.dto.response.UpdateAgentResponse;
import com.thistlestechnology.ilemimainapp.model.Agent;
import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.model.enums.Role;
import com.thistlestechnology.ilemimainapp.service.AgentService;
import com.thistlestechnology.ilemimainapp.service.AppUserService;
import com.thistlestechnology.ilemimainapp.service.ConfirmTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AgentServiceTest {
    @Autowired
    AgentService agentService;
    @Autowired
    ConfirmTokenService confirmTokenService;

    @Autowired
    AppUserService appUserService;
    AgentRegisterRequest agentRegisterRequest;
//    AgentRegisterResponse agentRegisterResponse;
    LoginAgentRequest loginAgentRequest;
//    LoginAgentResponse loginAgentResponse;

    @BeforeEach
    void setUp(){
        agentRegisterRequest = new  AgentRegisterRequest();
        agentRegisterRequest.setFirstName("John");
        agentRegisterRequest.setLastName("Doe");
        agentRegisterRequest.setPhoneNumber("08143567892");
        agentRegisterRequest.setEmail("dorisebele47@gmail.com");
        agentRegisterRequest.setPassword("123456");
//        loginAgentRequest = new LoginAgentRequest();
//        loginAgentRequest.setEmail(agentRegisterRequest.getEmail());
//        loginAgentRequest.setPassword(agentRegisterRequest.getPassword());

  }

    @Test
    void testThatAgentCanRegister(){
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        assertThat(agentRegisterResponse.getMessage()).isEqualTo
                ("agent has been registered successfully,go to your email and confirm email");
    }
//
    @Test
    void testThatAgentCanLogin(){
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        loginAgentRequest = new LoginAgentRequest();
        loginAgentRequest.setEmail(agentRegisterRequest.getEmail());
        loginAgentRequest.setPassword("123456");
        LoginAgentResponse loginResponse = agentService.loginAgent(loginAgentRequest);
        assertThat(loginResponse.getMessage()).isEqualTo("Login successful");
    }
    @Test
    void testThatAgentCannotLoginWithWrongPassword(){
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        LoginAgentRequest wrongLoginAgentRequest = new LoginAgentRequest();
        wrongLoginAgentRequest.setEmail("john@gmail.com");
        wrongLoginAgentRequest.setPassword("98765");
        LoginAgentResponse loginResponse = agentService.loginAgent(wrongLoginAgentRequest);
        assertEquals("Login failed, password or email mismatch",loginResponse.getMessage());
    }
    @Test
    void testThatARegisteredUserIsVerified(){
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        AppUser appUser = new AppUser();
        appUser.setUsername(agentRegisterRequest.getFirstName());
        appUser.setEmail(agentRegisterRequest.getEmail());
        appUser.setPassword(agentRegisterRequest.getPassword());
//        appUser.setRole(Role.AGENT);
//        String generatedToken = confirmTokenService.generateAndSaveToken(appUser);
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest();
//        confirmTokenRequest.setToken(generatedToken);
        confirmTokenRequest.setEmail(agentRegisterRequest.getEmail());
        agentService.verifyToken(confirmTokenRequest);
        assertEquals(true,agentService.isVerified(agentRegisterRequest.getEmail()));

    }
//    @Test
//    void testAgentProfileCanBeViewed(){
//        agentRegisterResponse =registerAgent();
//        assertNotNull(agentRegisterResponse.getId());
//        AgentProfileResponse agentProfileResponse = agentService
//                                    .viewAgentProfileById(agentRegisterResponse.getId());
//        assertNotNull(agentProfileResponse.getEmail());
//        Agent agent = agentService.findByEmail(agentProfileResponse.getEmail());
//
//        validateProperties( agent,  agentProfileResponse);
//    }
//    @Test
//    void testThatAgentCanEditeProfile(){
//        agentRegisterResponse = registerAgent();
//        assertNotNull(agentRegisterResponse.getId());
//
//        AgentProfileResponse agentProfileResponse = agentService
//                .viewAgentProfileById(agentRegisterResponse.getId());
//
//        Agent agent = agentService.findByEmail(agentProfileResponse.getEmail());
//
//        validateProperties(agent, agentProfileResponse);
//
//        UpdateAgentRequest updateAgentRequest =   UpdateAgentRequest.builder()
//                .id(agentRegisterResponse.getId())
//                .firstName("firstName")
//                .lastName("lastName")
//                .phoneNumber("phoneNumber")
//                .build();
//
////        try {
//            UpdateAgentResponse updateAgentResponse = agentService.updateAgentProfile(updateAgentRequest);
//            agent = agentService.findByEmail(agentProfileResponse.getEmail());
//
//            assertEquals(agent.getCountry(), updateAgentResponse.getCountry());
//            assertEquals(agent.getHomeAddress(), updateAgentResponse.getHomeAddress());
//            assertEquals(agent.getLocalGovt(), updateAgentResponse.getLocalGovt());
//            assertEquals(agent.getNIN(), updateAgentResponse.getNIN());
//            assertEquals(agent.getFirstName(), agentProfileResponse.getFirstName());
//            assertEquals(agent.getLastName(), agentProfileResponse.getLastName());
//
//            assertNotEquals(agent.getFirstName(), agentRegisterRequest.getFirstName());
//            assertNotEquals(agent.getLastName(), agentRegisterRequest.getLastName());
//            assertNotEquals(agent.getPhoneNumber(), agentRegisterRequest.getPhoneNumber());
//
////        }catch (Exception e){
////            System.out.println("error in testThatAgentCanEditeProfile");
////        }
//        agentProfileResponse = agentService
//                .viewAgentProfileById(agentRegisterResponse.getId());
//
////        agent = agentService.findByEmail(agentProfileResponse.getEmail());
//
//        validateProperties( agent,  agentProfileResponse);
//
//    }
//    @Test
//    void testForgotPassword(){
//        agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
//        assertNotNull(agentRegisterResponse.getId());
//        String agentEmail = agentRegisterRequest.getEmail();
//       var response =  appUserService.sendVerificationMail(agentEmail);
//       assertEquals("a token has been sent,please verify your email",response);
//    }
//    private void validateProperties(Agent agent, AgentProfileResponse agentProfileResponse){
//        assertEquals(agent.getCountry(), agentProfileResponse.getCountry());
//        assertEquals(agent.getHomeAddress(), agentProfileResponse.getHomeAddress());
//        assertEquals(agent.getLocalGovt(), agentProfileResponse.getLocalGovt());
//        assertEquals(agent.getNIN(), agentProfileResponse.getNIN());
//        assertEquals(agent.getFirstName(), agentProfileResponse.getFirstName());
//        assertEquals(agent.getLastName(), agentProfileResponse.getLastName());
//
//
//    }
//
//    private  AgentRegisterResponse registerAgent (){
//
//        String agentEmail = agentRegisterRequest.getEmail();
//        boolean agentExist = agentService.existsByEmail(agentEmail);
//
//        AgentRegisterResponse agentRegisterResponse;
//        if (agentExist) {
//            Agent agent = agentService.findByEmail(agentEmail);
//            agentRegisterResponse = AgentRegisterResponse.builder()
//                    .id(agent.getId())
//                    .message("agent has been registered successfully")
//                    .build(); ;
//            return agentRegisterResponse;
//        }
//        agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
//        return agentRegisterResponse;
//    }
//    private  AgentRegisterResponse registerAgent (String email ){
//        agentRegisterRequest.setEmail(email);
//        String agentEmail = agentRegisterRequest.getEmail();
//        boolean agentExist = agentService.existsByEmail(agentEmail);
//
//        AgentRegisterResponse agentRegisterResponse;
//        if (agentExist) {
//            Agent agent = agentService.findByEmail(agentEmail);
//            agentRegisterResponse = AgentRegisterResponse.builder()
//                    .id(agent.getId())
//                    .message("agent has been registered successfully")
//                    .build(); ;
//            return agentRegisterResponse;
//        }
//        agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
//        return agentRegisterResponse;
//    }
}
