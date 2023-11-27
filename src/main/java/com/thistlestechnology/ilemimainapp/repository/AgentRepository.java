package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends MongoRepository<Agent,String> {
    boolean existsByEmail(String email);

    Optional<Agent> findByEmail(String email);
}
