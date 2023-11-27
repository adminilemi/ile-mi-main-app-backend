package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.Advertiser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiserRepository extends MongoRepository<Advertiser,String> {
}
