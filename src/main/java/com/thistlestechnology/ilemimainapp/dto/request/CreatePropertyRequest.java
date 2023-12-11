package com.thistlestechnology.ilemimainapp.dto.request;

import com.thistlestechnology.ilemimainapp.model.PropertyLocation;
import com.thistlestechnology.ilemimainapp.model.enums.PropertyStatus;
import com.thistlestechnology.ilemimainapp.model.enums.PropertyType;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreatePropertyRequest {
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
    private List<MultipartFile> imageUrl;
    private List<MultipartFile> videoUrl;
}
