package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
//    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findAppUserByEmail(String email);

    AppUser findByEmail(String email);


}
