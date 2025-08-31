package com.danimo.user.information.application.usecases.updateinformation;

import com.danimo.user.information.infrastructure.inputadapters.rest.dto.UserInformationUpdateRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@AllArgsConstructor
public class UpdateUserInformationDto {

    @NotBlank
    private UUID userId;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    private String oldPassword;
    private String newPassword;
    private LocalDate birthdate;

    public static UpdateUserInformationDto toDomain(UserInformationUpdateRequest userInformationUpdateRequest) {
        return new UpdateUserInformationDto(userInformationUpdateRequest.getUserId(),userInformationUpdateRequest.getName(),
                userInformationUpdateRequest.getLastName(),userInformationUpdateRequest.getOldPassword(),userInformationUpdateRequest.getNewPassword(), userInformationUpdateRequest.getBirthdate());

    }

}
