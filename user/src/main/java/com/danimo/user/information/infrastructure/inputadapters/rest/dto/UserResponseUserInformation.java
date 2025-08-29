package com.danimo.user.information.infrastructure.inputadapters.rest.dto;

import com.danimo.user.user.domain.User;
import lombok.Value;

import java.util.UUID;

@Value
public class UserResponseUserInformation {

    private final UUID id;
    private final String username;
    private final String email;

    public static UserResponseUserInformation fromDomain(User user) {
        return new UserResponseUserInformation(user.getId(), user.getUsername(), user.getEmail());
    }
}
