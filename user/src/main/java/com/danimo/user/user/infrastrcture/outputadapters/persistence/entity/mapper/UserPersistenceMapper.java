package com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.mapper;

import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public User toDomain(UserDbEntity dbEntity){
        if (dbEntity == null) return null;

        return new User(dbEntity.getId(),
                dbEntity.getUsername(),
                dbEntity.getPassword(),
                dbEntity.getEmail());
    }

    public UserDbEntity toDbEntity(User user){
        if (user == null) return null;
        return new UserDbEntity(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail());
    }
}
