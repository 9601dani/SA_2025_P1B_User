package com.danimo.user.information.application.inputports;

import com.danimo.user.common.application.exceptions.CredentialsDoesntSame;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.usecases.updateinformation.UpdateUserInformationDto;
import com.danimo.user.information.domain.UserInformation;

public interface UpdatingUserInformationInputPort {
    UserInformation updateUserInformation(UpdateUserInformationDto updateUserInformationDto) throws UserNotFoundException, CredentialsDoesntSame;
}
