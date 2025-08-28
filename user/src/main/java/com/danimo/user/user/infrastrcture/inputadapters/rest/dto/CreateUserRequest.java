package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;


import com.danimo.user.user.application.usecases.createuser.CreateUserDto;
import lombok.Value;

@Value
public class CreateUserRequest {
    private final String username;
    private final String password;
    private final String email;
    private final String module;

    public CreateUserDto toDomain(){
        return new CreateUserDto(username, password, email, module);
    }

}
