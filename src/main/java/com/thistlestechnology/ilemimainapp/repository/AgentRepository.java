package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends MongoRepository<Agent,String> {
//    boolean existsByEmail(String email);
    @Query("{'appUser.email' : ?0}")
    boolean existsByAppUserEmail(String email);
    @Query("{'appUser.email' : ?0}")
    Optional<Agent> findByAppUserEmail(String email);
//    @Query("{ 'appUser.email' : ?0 }")
//    Optional<Agent> findAgentByAppUser_Email(String email);
}
