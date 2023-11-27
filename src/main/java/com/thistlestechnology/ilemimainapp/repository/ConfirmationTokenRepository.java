package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken,String> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Query("{'token': ?2, 'expireAt': {$gt: ?1}}")
    void setExpiredAt(LocalDateTime now, String token);

    void deleteTokenByExpiredAtBefore(LocalDateTime now);
}
