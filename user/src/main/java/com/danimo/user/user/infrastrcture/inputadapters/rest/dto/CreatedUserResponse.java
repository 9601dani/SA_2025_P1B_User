package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;

import com.danimo.user.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor
@Value
public class CreatedUserResponse {

    private final UUID id;
    private final String username;
    private final String email;
    private final String module;
    private final boolean isFirstTime;

    public static CreatedUserResponse fromDomain(User user) {
        return new CreatedUserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getModule(), user.isFirstTime());
    }

}
