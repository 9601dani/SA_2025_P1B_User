package com.danimo.user.user.application.outputports.persistence;

import com.danimo.user.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface FindingUserByUsernameOutputPort {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
}
