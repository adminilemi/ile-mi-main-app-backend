package com.thistlestechnology.ilemimainapp.serviceImplementation;

import com.thistlestechnology.ilemimainapp.dto.request.CreatePropertyRequest;
import com.thistlestechnology.ilemimainapp.dto.request.EditPropertyRequest;
import com.thistlestechnology.ilemimainapp.dto.response.CreatePropertyResponse;
import com.thistlestechnology.ilemimainapp.service.PropertyService;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Override
    public CreatePropertyResponse createProperty(CreatePropertyRequest createPropertyRequest) {
        return null;
    }

    @Override
    public String editProperty(EditPropertyRequest editPropertyRequest) {
        return null;
    }

    @Override
    public void viewAllPropertyListed() {

    }

    @Override
    public void viewPropertyById(String propertyId) {

    }

    @Override
    public void deleteProperty() {

    }

    @Override
    public void archiveProperty() {

    }

    @Override
    public void restorePropertyFromArchive() {

    }
}
