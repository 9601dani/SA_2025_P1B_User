package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;

import com.danimo.user.user.application.usecases.updateenabled.UpdateEnabledStateDto;
import lombok.Value;

@Value
public class UpdateUserEnabledStateRequest {
    private final String username;
    private final boolean enabled;

    public UpdateEnabledStateDto toDomain(){
        return new UpdateEnabledStateDto(enabled, username);
    }
}
