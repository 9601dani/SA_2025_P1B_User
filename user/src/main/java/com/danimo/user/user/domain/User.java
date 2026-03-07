package com.danimo.user.user.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import com.danimo.user.role.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
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
    private boolean firstTime;
    private boolean enabled;
    private UUID locationId;
    // private List<Role> roles;


}
