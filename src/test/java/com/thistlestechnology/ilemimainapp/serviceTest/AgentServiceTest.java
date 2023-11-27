package com.thistlestechnology.ilemimainapp.serviceTest;

import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.LoginAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.request.UpdateAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.response.AgentProfileResponse;
import com.thistlestechnology.ilemimainapp.dto.response.AgentRegisterResponse;
import com.thistlestechnology.ilemimainapp.dto.response.LoginAgentResponse;
import com.thistlestechnology.ilemimainapp.dto.response.UpdateAgentResponse;
import com.thistlestechnology.ilemimainapp.model.Agent;
import com.thistlestechnology.ilemimainapp.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AgentServiceTest {
    @Autowired
    AgentService agentService;

    AgentRegisterRequest agentRegisterRequest;
    AgentRegisterResponse agentRegisterResponse;
    LoginAgentRequest loginAgentRequest;
    LoginAgentResponse loginAgentResponse;

    @BeforeEach
    void setUp(){
        agentRegisterRequest = new  AgentRegisterRequest();
        agentRegisterRequest.setFirstName("John");
        agentRegisterRequest.setLastName("Doe");
        agentRegisterRequest.setPhoneNumber("08143567892");
        agentRegisterRequest.setEmail("john@gmail.com");
        agentRegisterRequest.setPassword("123456");
//        agentRegisterRequest.setHomeAddress("no 1 mosuu road");
//        agentRegisterRequest.setOfficeAddress("no 2 mosuu road");
//        agentRegisterRequest.setCountry("Nigeria");
//        agentRegisterRequest.setState("lagos");
//        agentRegisterRequest.setLocalGovt("local government area");
//        agentRegisterRequest.setNIN("234555899000000022");
        loginAgentRequest = new LoginAgentRequest();
        loginAgentRequest.setEmail(agentRegisterRequest.getEmail());
        loginAgentRequest.setPassword(agentRegisterRequest.getPassword());

  }

    @Test
    void testThatAgentCanRegister(){
        agentRegisterResponse = registerAgent();
        assertNotNull(agentRegisterResponse.getId());
    }

    @Test
    void testThatAgentCanLogin(){
        agentRegisterResponse = registerAgent();
        assertNotNull(agentRegisterResponse.getId());
        loginAgentResponse = agentService.loginAgent(loginAgentRequest);
        assertEquals("login successful",loginAgentResponse.getMessage());
    }
    @Test
    void testThatAgentCannotLoginWithWrongPassword(){
        agentRegisterResponse = registerAgent();
        assertNotNull(agentRegisterResponse.getId());

        LoginAgentRequest wrongLoginAgentRequest = new LoginAgentRequest();
        wrongLoginAgentRequest.setEmail("john@gmail.com");
        wrongLoginAgentRequest.setPassword("98765");

        loginAgentResponse = agentService.loginAgent(wrongLoginAgentRequest);
        assertEquals("login failed,password or email mismatch",loginAgentResponse.getMessage());
    }
    @Test
    void testAgentProfileCanBeViewed(){
        agentRegisterResponse =registerAgent();
        assertNotNull(agentRegisterResponse.getId());
        AgentProfileResponse agentProfileResponse = agentService
                                    .viewAgentProfileById(agentRegisterResponse.getId());
        assertNotNull(agentProfileResponse.getEmail());
        Agent agent = agentService.findByEmail(agentProfileResponse.getEmail());

        validateProperties( agent,  agentProfileResponse);
    }
    @Test
    void testThatAgentCanEditeProfile(){
        agentRegisterResponse = registerAgent();
        assertNotNull(agentRegisterResponse.getId());

        AgentProfileResponse agentProfileResponse = agentService
                .viewAgentProfileById(agentRegisterResponse.getId());

        Agent agent = agentService.findByEmail(agentProfileResponse.getEmail());

        validateProperties(agent, agentProfileResponse);

        UpdateAgentRequest updateAgentRequest =   UpdateAgentRequest.builder()
                .email(agentRegisterResponse.getId())
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("phoneNumber")
                .build();

//        try {
            UpdateAgentResponse updateAgentResponse = agentService.updateAgentProfile(updateAgentRequest);
            agent = agentService.findByEmail(agentProfileResponse.getEmail());

            assertEquals(agent.getCountry(), updateAgentResponse.getCountry());
            assertEquals(agent.getHomeAddress(), updateAgentResponse.getHomeAddress());
            assertEquals(agent.getLocalGovt(), updateAgentResponse.getLocalGovt());
            assertEquals(agent.getNIN(), updateAgentResponse.getNIN());
            assertEquals(agent.getFirstName(), agentProfileResponse.getFirstName());
            assertEquals(agent.getLastName(), agentProfileResponse.getLastName());

            assertNotEquals(agent.getFirstName(), agentRegisterRequest.getFirstName());
            assertNotEquals(agent.getLastName(), agentRegisterRequest.getLastName());
            assertNotEquals(agent.getPhoneNumber(), agentRegisterRequest.getPhoneNumber());

//        }catch (Exception e){
//            System.out.println("error in testThatAgentCanEditeProfile");
//        }
        agentProfileResponse = agentService
                .viewAgentProfileById(agentRegisterResponse.getId());

//        agent = agentService.findByEmail(agentProfileResponse.getEmail());

        validateProperties( agent,  agentProfileResponse);

    }
    @Test
    void testForgotPassword(){
        //given that i a user is registered
        //when he forgets password and clicks on forget password
        //check that the otp sent is the one he inputed
        agentRegisterResponse = registerAgent();
        assertNotNull(agentRegisterResponse.getId());
        String agentEmail = agentRegisterRequest.getEmail();
        var forgotPasswordResponse = agentService.forgotPassword(agentEmail);
        assertNotNull(forgotPasswordResponse.getToken());
    }
    private void validateProperties(Agent agent, AgentProfileResponse agentProfileResponse){
        assertEquals(agent.getCountry(), agentProfileResponse.getCountry());
        assertEquals(agent.getHomeAddress(), agentProfileResponse.getHomeAddress());
        assertEquals(agent.getLocalGovt(), agentProfileResponse.getLocalGovt());
        assertEquals(agent.getNIN(), agentProfileResponse.getNIN());
        assertEquals(agent.getFirstName(), agentProfileResponse.getFirstName());
        assertEquals(agent.getLastName(), agentProfileResponse.getLastName());


    }

    private  AgentRegisterResponse registerAgent (){
        String agentEmail = agentRegisterRequest.getEmail();
        boolean agentExist = agentService.existsByEmail(agentEmail);

        AgentRegisterResponse agentRegisterResponse;
        if (agentExist) {
            Agent agent = agentService.findByEmail(agentEmail);
            agentRegisterResponse = AgentRegisterResponse.builder()
                    .id(agent.getId())
                    .message("agent has been registered successfully")
                    .build(); ;
            return agentRegisterResponse;
        }
        agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        return agentRegisterResponse;
    }
}
