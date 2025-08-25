package com.danimo.user.user.application.outputports.persistence;

import com.danimo.user.user.domain.User;

public interface StoringUserOutputPort {
    User save(User user);
}
