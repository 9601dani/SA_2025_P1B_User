package com.danimo.user.application.inputports;

import com.danimo.common.application.exceptions.UserNotFoundException;
import com.danimo.user.domain.User;

public interface FindingUserByUsernameInputPort {

    User findByUsername(String username) throws UserNotFoundException;
}
