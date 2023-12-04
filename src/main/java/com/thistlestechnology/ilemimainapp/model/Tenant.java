package com.thistlestechnology.ilemimainapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "tenants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tenant {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @DBRef
    private AppUser appUser;
}
