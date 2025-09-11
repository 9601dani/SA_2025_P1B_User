package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;

import com.danimo.user.user.domain.User;
import lombok.Value;

@Value
public class UserListResponse {
    private final String username;
    private final String email;
    private final String module;
    private final boolean manager;
    private final boolean enabled;
    private final String locationId;

    public static UserListResponse fromDomain(User user) {
        return new UserListResponse(user.getUsername(), user.getEmail(), user.getModule(), user.isManager(), user.isEnabled(), user.getLocationId().toString());
    }
}
