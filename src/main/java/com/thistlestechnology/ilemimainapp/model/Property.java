package com.thistlestechnology.ilemimainapp.model;

import com.thistlestechnology.ilemimainapp.model.enums.PropertyStatus;
import com.thistlestechnology.ilemimainapp.model.enums.PropertyType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;


@Builder
@Document(collection = "property/listing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    @Id
    private String id;
    private String propertyName;
    private String pricePerMonth;
    private PropertyLocation location;
    private LocalDate dateListed;
    private LocalDate availableDate;
    private String leaseDuration;
    private String numberOfBath;
    private String numberOfBedroom;
    private String description;
    private PropertyType propertyType;
    private PropertyStatus propertyStatus;
    private List<String> listOfAmenities;
    private List<String> imageUrl;
    private List<String> videoUrl;

}
