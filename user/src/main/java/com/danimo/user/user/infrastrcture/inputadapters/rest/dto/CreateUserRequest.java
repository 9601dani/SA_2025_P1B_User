package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;


import com.danimo.user.user.application.usecases.createuser.CreateUserDto;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class CreateUserRequest {
    private final String username;
    private final String password;
    private final String email;
    private final String module;
    private final BigDecimal salaryPerWeek;
    private final boolean isAdmin;
    private final String locationId;

    public CreateUserDto toDomain(){
        return new CreateUserDto(username, password, email, module, salaryPerWeek,isAdmin, UUID.fromString(locationId));
    }

}
