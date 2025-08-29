package com.danimo.user.information.application.outputports.persistence;

import com.danimo.user.information.domain.UserInformation;

public interface StoringUserInformationOutputPort {
    UserInformation save(UserInformation userInformation);
}
