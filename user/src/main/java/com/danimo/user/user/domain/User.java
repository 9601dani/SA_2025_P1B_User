package com.danimo.user.user.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@DomainEntity
@AllArgsConstructor
@Builder
@Setter
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String module;
    private boolean firstTime;
    private boolean manager;
    private boolean enabled;








}
