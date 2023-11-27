package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends MongoRepository<Tenant,String> {
}
