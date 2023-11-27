package com.thistlestechnology.ilemimainapp.service;

import com.thistlestechnology.ilemimainapp.dto.request.EmailRequest;

public interface EmailService {
    void sendOTP(EmailRequest emailRequest);
}
