package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;

import com.danimo.user.user.domain.User;
import lombok.Value;

@Value
public class UserListResponse {
    private final String id;
    private final String username;
    private final String email;
    private final boolean enabled;
    private final String locationId;

    public static UserListResponse fromDomain(User user) {
        return new UserListResponse(user.getId().toString(), user.getUsername(), user.getEmail(), user.isEnabled(), user.getLocationId().toString());
    }
}
