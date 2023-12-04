package com.thistlestechnology.ilemimainapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "_token")
public class ConfirmationToken {
    @Id
    private String id;
    private String token;
    private LocalDateTime createdAt;

    private LocalDateTime confirmAt;
    private LocalDateTime expiredAt;
//    @DBRef
//    private AppUser appUser;
    private String userEmail;
    public ConfirmationToken(String token,LocalDateTime createdAt,LocalDateTime expiredAt,String userEmail){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.userEmail = userEmail;
    }

}
