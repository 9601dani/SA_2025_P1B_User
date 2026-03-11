package com.danimo.user.module.infrastructure.outputadapters.persistence.entity.mapper;

import com.danimo.user.module.domain.Module;
import com.danimo.user.module.infrastructure.outputadapters.persistence.entity.ModuleDbEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ModulePersistenceMapper {

    public Module toDomain(ModuleDbEntity dbEntity){
        if(dbEntity == null) return null;

        return new Module(dbEntity.getId(),
                dbEntity.getName(),
                dbEntity.getDirection(),
                dbEntity.getIcon(),
                dbEntity.getIsAvailable(),
                dbEntity.getCreatedAt());
    }

    public ModuleDbEntity toDbEntity(Module module){
        if(module == null) return null;

        return new ModuleDbEntity(module.getId(),
                module.getName(),
                module.getDirection(),
                module.getIcon(),
                module.getIsAvailable(),
                module.getCreatedAt(),
                new ArrayList<>());
    }
}
