package com.danimo.user.information.application.outputports.persistence;

import com.danimo.user.information.domain.UserInformation;

import java.util.Optional;
import java.util.UUID;

public interface FindingUserInformationOutputPort {

    Optional<UserInformation> findUserInformationByUserId(UUID userId);
}
