package com.danimo.user.information.application.inputports;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.domain.UserInformation;

public interface FindingUserInformationInputPort {
    UserInformation findUserInformationByUserId(String userId) throws UserNotFoundException;
}
