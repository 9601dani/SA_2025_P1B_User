package com.danimo.user.module.application.usecases.getmodules;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.module.application.inputports.GetModulesByUserIdInputPort;
import com.danimo.user.module.application.outputports.persistence.FindingModulesOutputPort;
import com.danimo.user.module.domain.Module;
import com.danimo.user.page.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@UseCase
public class ListModulesByUserIdUseCase implements GetModulesByUserIdInputPort {
    private final FindingModulesOutputPort findingPagesOutputPort;

    @Autowired
    public ListModulesByUserIdUseCase(FindingModulesOutputPort findingPagesOutputPort) {
        this.findingPagesOutputPort = findingPagesOutputPort;
    }

    @Override
    public List<Page> getPagesByUserId(UUID userId) {
        return findingPagesOutputPort.findingModulesByUserId(userId);
    }
}
