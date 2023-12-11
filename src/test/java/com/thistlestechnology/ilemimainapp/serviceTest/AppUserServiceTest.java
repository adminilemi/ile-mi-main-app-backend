package com.thistlestechnology.ilemimainapp.serviceTest;

import com.thistlestechnology.ilemimainapp.dto.request.AgentRegisterRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ConfirmTokenRequest;
import com.thistlestechnology.ilemimainapp.dto.request.LoginAgentRequest;
import com.thistlestechnology.ilemimainapp.dto.request.ResetPasswordRequest;
import com.thistlestechnology.ilemimainapp.dto.response.AgentRegisterResponse;
import com.thistlestechnology.ilemimainapp.dto.response.LoginAgentResponse;
import com.thistlestechnology.ilemimainapp.service.AgentService;
import com.thistlestechnology.ilemimainapp.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppUserServiceTest {
    AgentRegisterRequest agentRegisterRequest;
    ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
    LoginAgentRequest loginAgentRequest = new LoginAgentRequest();
    @Autowired
    AgentService agentService;
    @Autowired
    AppUserService appUserService;

    @BeforeEach
    void setUp(){
        agentRegisterRequest = new AgentRegisterRequest();
        agentRegisterRequest.setFirstName("John");
        agentRegisterRequest.setLastName("Doe");
        agentRegisterRequest.setPhoneNumber("08143567892");
        agentRegisterRequest.setEmail("dorisebele47@gmail.com");
        agentRegisterRequest.setPassword("123456");
        resetPasswordRequest.setAppUserEmail("dorisebele47@gmail.com");
        resetPasswordRequest.setNewPassword("123457");
        resetPasswordRequest.setConfirmNewPassword("123457");
    }
    @Test
    void testSendVerificationMailForForgotPassword(){
        //given that a user exist
        //when i send otp to the email,
        //check that
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        assertThat(agentRegisterResponse.getMessage()).isEqualTo
                ("agent has been registered successfully,go to your email and confirm email");
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest();
        confirmTokenRequest.setToken(agentRegisterResponse.getToken());
        confirmTokenRequest.setEmail(agentRegisterRequest.getEmail());
        agentService.verifyToken(confirmTokenRequest);
        var response =appUserService.sendVerificationMail(agentRegisterRequest.getEmail());
        assertEquals(response.getMessage(),"an email has been sent,go to your email and verify");


    }
    @Test
    void testThatUnverifiedUserCannotGetVerificationMailForForgotPassword(){
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        assertThat(agentRegisterResponse.getMessage()).isEqualTo
                ("agent has been registered successfully,go to your email and confirm email");
        var response =appUserService.sendVerificationMail(agentRegisterRequest.getEmail());
        assertEquals(response.getMessage(),"email not sent, user might not be registered");
    }
    @Test
    void testThatAUserCanResetTheirPassword(){
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        assertThat(agentRegisterResponse.getMessage()).isEqualTo
                ("agent has been registered successfully,go to your email and confirm email");
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest();
        confirmTokenRequest.setToken(agentRegisterResponse.getToken());
        confirmTokenRequest.setEmail(agentRegisterRequest.getEmail());
        agentService.verifyToken(confirmTokenRequest);
        var response =appUserService.sendVerificationMail(agentRegisterRequest.getEmail());
        assertEquals(response.getMessage(),"an email has been sent,go to your email and verify");
        resetPasswordRequest.setAppUserEmail("dorisebele47@gmail.com");
        resetPasswordRequest.setNewPassword("123457");
        resetPasswordRequest.setConfirmNewPassword("123457");
        resetPasswordRequest.setReceivedOtp(response.getToken());
        var resetPasswordRes = appUserService.resetPassword(resetPasswordRequest);
        assertEquals("password reset successful",resetPasswordRes.getMessage());

    }
    @Test
    void testThatUserCanLoginWithNewPassword(){
        AgentRegisterResponse agentRegisterResponse = agentService.registerAgent(agentRegisterRequest);
        assertThat(agentRegisterResponse.getAgentId()).isNotNull();
        assertThat(agentRegisterResponse.getMessage()).isEqualTo
                ("agent has been registered successfully,go to your email and confirm email");
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest();
        confirmTokenRequest.setToken(agentRegisterResponse.getToken());
        confirmTokenRequest.setEmail(agentRegisterRequest.getEmail());
        agentService.verifyToken(confirmTokenRequest);
        var response =appUserService.sendVerificationMail(agentRegisterRequest.getEmail());
        assertEquals(response.getMessage(),"an email has been sent,go to your email and verify");
        resetPasswordRequest.setAppUserEmail("dorisebele47@gmail.com");
        resetPasswordRequest.setNewPassword("123457");
        resetPasswordRequest.setConfirmNewPassword("123457");
        resetPasswordRequest.setReceivedOtp(response.getToken());
        var resetPasswordRes = appUserService.resetPassword(resetPasswordRequest);
        assertEquals("password reset successful",resetPasswordRes.getMessage());
        loginAgentRequest = new LoginAgentRequest();
        loginAgentRequest.setEmail(agentRegisterRequest.getEmail());
        loginAgentRequest.setPassword("123457");
        LoginAgentResponse loginResponse = agentService.loginAgent(loginAgentRequest);
        assertThat(loginResponse.getMessage()).isEqualTo("login successful");
    }


}
