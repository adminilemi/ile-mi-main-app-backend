package com.thistlestechnology.ilemimainapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "_token")
public class ConfirmationToken {
    private String id;
    private String token;
    private LocalDateTime createdAt;

    private LocalDateTime confirmAt;
    private LocalDateTime expiredAt;
    @DBRef
    private Agent agent;


    public ConfirmationToken(String token,LocalDateTime createdAt,LocalDateTime expiredAt,Agent agent){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.agent = agent;
    }

}
