package com.danimo.user.page.application.usecases.findpages;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.module.application.outputports.persistence.FindingModulesOutputPort;
import com.danimo.user.page.application.inputports.FindPageByModuleInputPort;
import com.danimo.user.page.application.outputports.FindingPagesByModuleOutputPort;
import com.danimo.user.page.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@UseCase
public class FindPagesByModuleIdUseCase implements FindPageByModuleInputPort {
    private final FindingPagesByModuleOutputPort findingPagesByModuleOutputPort;

    @Autowired
    public FindPagesByModuleIdUseCase(FindingPagesByModuleOutputPort findingPagesByModuleOutputPort) {
        this.findingPagesByModuleOutputPort = findingPagesByModuleOutputPort;
    }

    @Override
    public List<Page> findByUserId(UUID userId) {
        return findingPagesByModuleOutputPort.findingPagesByUserId(userId);
    }
}
