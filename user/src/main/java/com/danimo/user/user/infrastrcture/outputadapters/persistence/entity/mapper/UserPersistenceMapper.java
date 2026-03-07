package com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.mapper;

import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserPersistenceMapper {

    public User toDomain(UserDbEntity dbEntity){
        if (dbEntity == null) return null;
        return new User(dbEntity.getId(),
                dbEntity.getUsername(),
                dbEntity.getPassword(),
                dbEntity.getEmail(),
                dbEntity.isFirstTime(),
                dbEntity.isEnabled(),
                dbEntity.getLocationId());
    }

    public UserDbEntity toDbEntity(User user){
        if (user == null) return null;
        return new UserDbEntity(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isFirstTime(),
                user.isEnabled(),
                user.getLocationId(),
                new ArrayList<>());
    }
}
