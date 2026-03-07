package com.danimo.user.role.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@DomainEntity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
