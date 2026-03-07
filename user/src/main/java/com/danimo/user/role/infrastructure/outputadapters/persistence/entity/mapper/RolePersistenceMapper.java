package com.danimo.user.role.infrastructure.outputadapters.persistence.entity.mapper;

import com.danimo.user.role.domain.Role;
import com.danimo.user.role.infrastructure.outputadapters.persistence.entity.RoleDbEntity;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RolePersistenceMapper {

    private final UserPersistenceMapper userPersistenceMapper;

    public RolePersistenceMapper(UserPersistenceMapper userPersistenceMapper) {
        this.userPersistenceMapper = userPersistenceMapper;
    }

    public Role toDomain(RoleDbEntity dbEntity){
        if(dbEntity == null) return null;

        return new Role(dbEntity.getId(),
                dbEntity.getName(),
                dbEntity.getDescription(),
                dbEntity.getCreatedAt());
    }

    public RoleDbEntity toDb(Role role){
        if(role == null) return null;

        return new RoleDbEntity(role.getId(),
                role.getName(),
                role.getDescription(),
                role.getCreatedAt(),
                new ArrayList<>());
    }
}
