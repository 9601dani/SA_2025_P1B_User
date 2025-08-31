package com.danimo.user.information.infrastructure.inputadapters.rest.dto;

import com.danimo.user.information.application.usecases.updateinformation.UpdateUserInformationDto;
import com.danimo.user.information.domain.UserInformation;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class UserInformationUpdateRequest {
    private UUID userId;
    private String name;
    private String lastName;
    private String newPassword;
    private String oldPassword;
    private LocalDate birthdate;

}
