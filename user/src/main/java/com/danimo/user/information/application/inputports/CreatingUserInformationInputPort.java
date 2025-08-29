package com.danimo.user.information.application.inputports;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.usecases.createuserinformation.CreateUserInformationDto;
import com.danimo.user.information.domain.UserInformation;
import jakarta.validation.Valid;

public interface CreatingUserInformationInputPort {

    UserInformation createUserInformation(@Valid CreateUserInformationDto createUserInformationDto) throws UserNotFoundException;
}
