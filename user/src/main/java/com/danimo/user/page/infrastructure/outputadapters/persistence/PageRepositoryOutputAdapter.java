package com.danimo.user.page.infrastructure.outputadapters.persistence;

import com.danimo.user.common.infrastructure.annotations.PersistenceAdapter;
import com.danimo.user.page.application.outputports.FindingPagesByModuleOutputPort;
import com.danimo.user.page.domain.Page;
import com.danimo.user.page.infrastructure.outputadapters.persistence.entity.mapper.PagePersistenceMapper;
import com.danimo.user.page.infrastructure.outputadapters.persistence.repository.PageDbEntityJpaRepository;
import com.danimo.user.role.infrastructure.outputadapters.persistence.entity.RolePageDbEntity;
import com.danimo.user.role.infrastructure.outputadapters.persistence.repository.RolePageDbEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
public class PageRepositoryOutputAdapter implements FindingPagesByModuleOutputPort {

    private final PageDbEntityJpaRepository repository;
    private final RolePageDbEntityRepository roleRepository;
    private final PagePersistenceMapper pagePersistenceMapper;
    private final PagePersistenceMapper mapper;

    @Autowired
    public PageRepositoryOutputAdapter(PageDbEntityJpaRepository repository, RolePageDbEntityRepository roleRepository, PagePersistenceMapper pagePersistenceMapper, PagePersistenceMapper mapper) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.pagePersistenceMapper = pagePersistenceMapper;
        this.mapper = mapper;
    }


    @Override
    public List<Page> findingPagesByUserId(UUID userId) {
        return roleRepository.findPagesByUserId(userId)
                .stream()
                .map(pagePersistenceMapper::toDomain)
                .toList();
    }
}
