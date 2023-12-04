package com.thistlestechnology.ilemimainapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "advertisers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Advertiser {
    @Id
    private String id;
    private String adsPackage;
    private String businessName;
    private String phoneNumber;
    @DBRef
    private AppUser appUser;
}
