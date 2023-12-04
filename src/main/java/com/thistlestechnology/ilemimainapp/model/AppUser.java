package com.thistlestechnology.ilemimainapp.model;


import com.thistlestechnology.ilemimainapp.model.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "app_user")
public class AppUser {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean isVerified;
    private Set<Role> userRoles;


}
