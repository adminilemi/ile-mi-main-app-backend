package com.thistlestechnology.ilemimainapp.repository;

import com.thistlestechnology.ilemimainapp.model.AppUser;
import com.thistlestechnology.ilemimainapp.model.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken,String> {

    Optional<ConfirmationToken> findByToken(String token);
//    ConfirmationToken findConfirmationTokenByAppUser(AppUser appUser);
//    ConfirmationToken findConfirmationTokenByTokenAndAppUser_Email(String token, String email);


    @Transactional
    @Query("{'token': ?2, 'expireAt': {$gt: ?1}}")
    void setExpiredAt(LocalDateTime now, String token);
    @Transactional
    void deleteTokenByExpiredAtBefore(LocalDateTime now);

    ConfirmationToken findConfirmationTokenByToken(String token);
    @Transactional
    @Query("UPDATE ConfirmationToken confirmationToken" +
            " SET confirmationToken.confirmedAt = ?1" +
            " WHERE confirmationToken.token = ?2")
    void setConfirmedAt(LocalDateTime now, String token);

    @Transactional
    void deleteConfirmationTokensByExpiredAtBefore(LocalDateTime now);

//    ConfirmationToken findByAppUser_EmailAndToken(String token, String email);

    ConfirmationToken findConfirmationTokenByUserEmail(String email);

}
