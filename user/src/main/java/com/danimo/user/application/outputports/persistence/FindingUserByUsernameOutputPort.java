package com.danimo.user.application.outputports.persistence;

import com.danimo.user.domain.User;

import java.util.Optional;

public interface FindingUserByUsernameOutputPort {
    Optional<User> findByUsername(String username);
}
