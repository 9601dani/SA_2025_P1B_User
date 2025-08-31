package com.danimo.user.user.application.usecases.updateenabled;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.common.application.exceptions.UserNotFoundException;
import com.danimo.user.user.application.inputports.UpdatingEnabledStateInputPort;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.user.application.outputports.persistence.UpdatingEnabledStateOutputPort;
import com.danimo.user.user.domain.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;


@UseCase
public class UpdateEnabledStateUseCase implements UpdatingEnabledStateInputPort {

    private final UpdatingEnabledStateOutputPort outputPort;
    private final StoringUserOutputPort storingUserOutputPort;
    @Autowired
    public UpdateEnabledStateUseCase(UpdatingEnabledStateOutputPort outputPort,
                                     StoringUserOutputPort storingUserOutputPort) {
        this.outputPort = outputPort;
        this.storingUserOutputPort = storingUserOutputPort;
    }


    @Override
    @Transactional
    public User updateEnabledState(@Valid UpdateEnabledStateDto dto) {
        User user = this.outputPort.updateEnabledState(dto)
                .orElseThrow(()-> new UserNotFoundException(dto.username()));

        user.setEnabled(dto.enabled());

        this.storingUserOutputPort.save(user);

        return user;
    }

}
