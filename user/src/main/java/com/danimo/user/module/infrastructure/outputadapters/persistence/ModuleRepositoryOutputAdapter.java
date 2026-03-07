package com.danimo.user.module.infrastructure.outputadapters.persistence;

import com.danimo.user.common.infrastructure.annotations.PersistenceAdapter;
import com.danimo.user.module.application.outputports.persistence.FindingModulesOutputPort;
import com.danimo.user.module.domain.Module;
import com.danimo.user.module.infrastructure.outputadapters.persistence.entity.mapper.ModulePersistenceMapper;
import com.danimo.user.module.infrastructure.outputadapters.persistence.repository.ModuleDbEntityJpaRepository;
import com.danimo.user.page.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
public class ModuleRepositoryOutputAdapter implements FindingModulesOutputPort {

    private final ModuleDbEntityJpaRepository repository;
    private final ModulePersistenceMapper mapper;

    @Autowired
    public ModuleRepositoryOutputAdapter(ModuleDbEntityJpaRepository repository, ModulePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Page> findingModulesByUserId(UUID id) {
        return List.of();
    }
}
