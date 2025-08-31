package com.danimo.user.user.application.inputports;

import com.danimo.user.user.application.usecases.updateenabled.UpdateEnabledStateDto;
import com.danimo.user.user.domain.User;

import java.util.Optional;

public interface UpdatingEnabledStateInputPort {
    User updateEnabledState(UpdateEnabledStateDto dto);
}
