package com.danimo.user.module.application.outputports.persistence;

import com.danimo.user.module.domain.Module;
import com.danimo.user.page.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FindingModulesOutputPort {
    List<Page> findingModulesByUserId(UUID id);
}
