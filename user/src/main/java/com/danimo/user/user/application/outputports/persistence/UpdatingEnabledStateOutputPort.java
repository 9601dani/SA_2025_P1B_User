package com.danimo.user.user.application.outputports.persistence;

import com.danimo.user.user.application.usecases.updateenabled.UpdateEnabledStateDto;
import com.danimo.user.user.domain.User;

import java.util.Optional;

public interface UpdatingEnabledStateOutputPort {
    Optional<User> updateEnabledState(UpdateEnabledStateDto dto);
}
