package com.thistlestechnology.ilemimainapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class PropertyLocation {
    @Id
    private String id;
   private String streetAddress;
   private String unitNumber;
   private String city;
   private String state;
    private String zipcode;

}
