package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin,String> {
}
