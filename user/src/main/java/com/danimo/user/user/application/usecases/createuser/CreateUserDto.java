package com.danimo.user.user.application.usecases.createuser;

import com.danimo.user.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@AllArgsConstructor
public class CreateUserDto {


    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    private String module;
    private boolean firstTime = true ;
    private BigDecimal salaryPerWeek;

    public User toDomain(){
        return User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .email(email)
                .module(module)
                .firstTime(firstTime)
                .build();
    }

    public User toDomainPass(String password){
        return User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .email(email)
                .module(module)
                .firstTime(firstTime)
                .build();
    }


}
