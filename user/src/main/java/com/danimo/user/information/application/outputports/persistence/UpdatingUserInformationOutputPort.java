package com.danimo.user.information.application.outputports.persistence;

import com.danimo.user.information.domain.UserInformation;

import java.util.Optional;

public interface UpdatingUserInformationOutputPort {
    Optional<UserInformation> updateUserInformation(UserInformation userInformation);
}
