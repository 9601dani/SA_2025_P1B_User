package com.danimo.user.information.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import com.danimo.user.information.application.usecases.updateinformation.UpdateUserInformationDto;
import com.danimo.user.user.application.outputports.security.PasswordEncoderPort;
import com.danimo.user.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@DomainEntity
@AllArgsConstructor
@Builder
@Setter
public class UserInformation {

    private UserInformationId id;
    private String name;
    private String lastName;
    private BigDecimal salaryPerWeek;
    private LocalDateTime createdAt;
    private LocalDate birthdate;
    private User user;

}
