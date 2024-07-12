package com.amar.userservice.repository;

import com.amar.userservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token save(Token token);

    Optional<Token> findByValueAndIsDeleted(String token, boolean isDeleted);

    Optional<Token> findByValueAndIsDeletedAndExpiryAtIsAfter(String value, boolean isDeleted, Date date);
}
