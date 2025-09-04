package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;

import com.danimo.user.user.domain.User;
import lombok.Value;

import java.util.UUID;

@Value
public class UserResponse {

    private final UUID id;
    private final String username;
    private final String password;
    private final String email;
    private final String module;
    private final boolean firstTime;
    private final boolean manager;
    private final boolean enabled;
    private final String locationId;


    public static UserResponse fromDomain(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getModule(), user.isFirstTime(),
                user.isManager(), user.isEnabled(), user.getLocationId().toString());
    }
}
