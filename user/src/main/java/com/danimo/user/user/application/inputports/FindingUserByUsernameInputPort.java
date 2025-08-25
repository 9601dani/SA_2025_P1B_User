package com.danimo.user.user.application.inputports;

import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.user.domain.User;

public interface FindingUserByUsernameInputPort {

    User findByUsername(String username) throws UserNotFoundException;
}
