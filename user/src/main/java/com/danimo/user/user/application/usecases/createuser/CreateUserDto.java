package com.danimo.user.user.application.usecases.createuser;

import com.danimo.user.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Setter
public class CreateUserDto {


    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;

    public User toDomain(){
        return User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .email(email)
                .build();
    }

    public User toDomainPass(String password){
        return User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .email(email)
                .build();
    }


}
