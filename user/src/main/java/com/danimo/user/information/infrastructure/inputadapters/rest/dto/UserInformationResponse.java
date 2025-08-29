package com.danimo.user.information.infrastructure.inputadapters.rest.dto;

import com.danimo.user.information.domain.UserInformation;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Value
public class UserInformationResponse {

    private UUID id;
    private String name;
    private String lastName;
    private BigDecimal salaryPerWeek;
    private LocalDateTime createdAt;
    private LocalDate birthdate;
    private UserResponseUserInformation userInformation;

    public static UserInformationResponse fromDomain(UserInformation userInformation) {
        return new UserInformationResponse(userInformation.getId(), userInformation.getName(), userInformation.getLastName(),
                userInformation.getSalaryPerWeek(), userInformation.getCreatedAt(), userInformation.getBirthdate(),
                new UserResponseUserInformation(userInformation.getUser().getId(), userInformation.getUser().getUsername(),
                        userInformation.getUser().getEmail()));
    }

}
