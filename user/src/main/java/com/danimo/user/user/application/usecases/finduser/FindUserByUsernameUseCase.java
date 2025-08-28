package com.danimo.user.user.application.usecases.finduser;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.user.application.inputports.FindingUserByUsernameInputPort;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class FindUserByUsernameUseCase implements FindingUserByUsernameInputPort {

    private final FindingUserByUsernameOutputPort findingByIdPort;

    @Autowired
    public FindUserByUsernameUseCase(FindingUserByUsernameOutputPort findingByIdPort) {
        this.findingByIdPort = findingByIdPort;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        return findingByIdPort.findByUsername(username)
                .or(() -> findingByIdPort.findByEmail(username))
                .or(() -> findingByIdPort.findById(username))
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
