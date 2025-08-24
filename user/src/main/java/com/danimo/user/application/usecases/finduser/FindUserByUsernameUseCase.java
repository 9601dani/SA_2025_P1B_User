package com.danimo.user.application.usecases.finduser;

import com.danimo.common.application.annotations.UseCase;
import com.danimo.common.application.exceptions.UserNotFoundException;
import com.danimo.user.application.inputports.FindingUserByUsernameInputPort;
import com.danimo.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.domain.User;
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
                .orElseThrow(() -> new UserNotFoundException(User.class));
    }
}
