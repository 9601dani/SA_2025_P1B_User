package com.danimo.user.user.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@DomainEntity
@AllArgsConstructor
public class UserInformation {

    private UUID id;
    private String name;
    private String lastName;
    private BigDecimal salaryPerWeek;
    private LocalDateTime createdAt;
}
