package com.week10springsecurity.repository;

import com.week10springsecurity.entity.Token;
import com.week10springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
    List<Token> findAllByUser(User user);
}
