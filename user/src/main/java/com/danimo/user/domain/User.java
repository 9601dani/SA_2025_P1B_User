package com.danimo.user.domain;

import com.danimo.common.domain.annotations.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@DomainEntity
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;



}
