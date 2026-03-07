package com.danimo.user.page.infrastructure.outputadapters.persistence.entity.mapper;

import com.danimo.user.module.infrastructure.outputadapters.persistence.entity.mapper.ModulePersistenceMapper;
import com.danimo.user.page.domain.Page;
import com.danimo.user.page.infrastructure.outputadapters.persistence.entity.PageDbEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PagePersistenceMapper {
    private final ModulePersistenceMapper modulePersistenceMapper = new ModulePersistenceMapper();
    public Page toDomain(PageDbEntity dbEntity){
        if(dbEntity == null) return null;

        return new Page(dbEntity.getId(),
                dbEntity.getName(),
                dbEntity.getPath(),
                modulePersistenceMapper.toDomain(dbEntity.getModule()),
                dbEntity.getIsAvailable(),
                dbEntity.getCreatedAt());
    }

    public PageDbEntity toDbEntity(Page page){
        if(page == null) return null;
        return new PageDbEntity(page.getId(),
                page.getName(),
                page.getPath(),
                modulePersistenceMapper.toDbEntity(page.getModule()),
                page.getIsAvailable(),
                page.getCreatedAt(),
                new ArrayList<>());
    }
}
