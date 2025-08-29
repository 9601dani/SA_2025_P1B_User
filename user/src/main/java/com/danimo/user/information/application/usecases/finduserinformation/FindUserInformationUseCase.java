package com.danimo.user.information.application.usecases.finduserinformation;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.information.application.inputports.FindingUserInformationInputPort;
import com.danimo.user.information.application.outputports.persistence.FindingUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@UseCase
public class FindUserInformationUseCase implements FindingUserInformationInputPort {

    private final FindingUserInformationOutputPort findingUserInformationOutputPort;

    @Autowired
    public FindUserInformationUseCase(FindingUserInformationOutputPort findingUserInformationOutputPort) {
        this.findingUserInformationOutputPort = findingUserInformationOutputPort;
    }

    @Override
    public UserInformation findUserInformationByUserId(String userId) throws UserNotFoundException {
        UserInformation userInformation =  this.findingUserInformationOutputPort.findUserInformationByUserId(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        return userInformation;
    }
}
