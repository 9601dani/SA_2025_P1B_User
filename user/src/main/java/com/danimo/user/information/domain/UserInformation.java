package com.danimo.user.information.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import com.danimo.user.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@DomainEntity
@AllArgsConstructor
@Builder
public class UserInformation {

    private UUID id;
    private String name;
    private String lastName;
    private BigDecimal salaryPerWeek;
    private LocalDateTime createdAt;
    private LocalDate birthdate;
    private User user;
}
