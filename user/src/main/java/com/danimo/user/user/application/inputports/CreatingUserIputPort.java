package com.danimo.user.user.application.inputports;

import com.danimo.user.common.application.exceptions.UserAlreadyExistsException;
import com.danimo.user.user.application.usecases.createuser.CreateUserDto;
import com.danimo.user.user.domain.User;
import jakarta.validation.Valid;

public interface CreatingUserIputPort {

    User createUser(@Valid CreateUserDto userDto) throws UserAlreadyExistsException;
}
