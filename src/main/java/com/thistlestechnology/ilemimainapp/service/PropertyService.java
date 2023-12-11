package com.thistlestechnology.ilemimainapp.service;

import com.thistlestechnology.ilemimainapp.dto.request.CreatePropertyRequest;
import com.thistlestechnology.ilemimainapp.dto.request.EditPropertyRequest;
import com.thistlestechnology.ilemimainapp.dto.response.CreatePropertyResponse;

public interface PropertyService {
    CreatePropertyResponse createProperty(CreatePropertyRequest createPropertyRequest);
    String editProperty(EditPropertyRequest editPropertyRequest);
    void viewAllPropertyListed();
    void viewPropertyById(String propertyId);
    void deleteProperty();
    void archiveProperty();
    void restorePropertyFromArchive();
}
