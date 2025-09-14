package com.danimo.user.information.application.usecases.createuserinformation;

import com.danimo.user.information.domain.UserInformationId;
import com.danimo.user.user.domain.User;
import com.danimo.user.information.domain.UserInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@AllArgsConstructor
public class CreateUserInformationDto {

    private BigDecimal salaryPerWeek;
    private UUID userId;

    public UserInformation toDomain(){
        return UserInformation.builder()
                .id(UserInformationId.generate())
                .createdAt(LocalDateTime.now())
                .salaryPerWeek(salaryPerWeek)
                .user(User.builder().id(userId).build())
                .build();
    }


}
