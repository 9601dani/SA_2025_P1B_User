package com.danimo.user.information.domain;

import java.util.UUID;

public class UserInformationId {

    private final UUID id;

    private UserInformationId(UUID id) {
        this.id = id;
    }

    public static UserInformationId generate() {
        return new UserInformationId(UUID.randomUUID());
    }

    public static UserInformationId fromUUID(UUID uuid) {
        return new UserInformationId(uuid);
    }
    public static UUID toUUID(UserInformationId userInformationId) {
        return userInformationId.id;
    }
}
