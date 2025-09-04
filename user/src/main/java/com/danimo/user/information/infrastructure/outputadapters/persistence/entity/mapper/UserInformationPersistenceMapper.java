package com.danimo.user.information.infrastructure.outputadapters.persistence.entity.mapper;

import com.danimo.user.information.domain.UserInformationId;
import com.danimo.user.user.domain.User;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import com.danimo.user.information.infrastructure.outputadapters.persistence.entity.UserInformationDbEntity;
import org.springframework.stereotype.Component;

@Component
public class UserInformationPersistenceMapper {

    public UserInformation toDomain(UserInformationDbEntity dbEntity){
        if(dbEntity == null) return null;

        return new UserInformation(
                UserInformationId.fromUUID(dbEntity.getId()),
                dbEntity.getName(),
                dbEntity.getLastName(),
                dbEntity.getSalaryPerWeek(),
                dbEntity.getCreatedAt(),
                dbEntity.getBirthdate(),
                new User(
                        dbEntity.getUser().getId(),
                        dbEntity.getUser().getUsername(),
                        dbEntity.getUser().getPassword(),
                        dbEntity.getUser().getEmail(),
                        dbEntity.getUser().getModule(),
                        dbEntity.getUser().isFirstTime(),
                        dbEntity.getUser().isManager(),
                        dbEntity.getUser().isEnabled(),
                        dbEntity.getUser().getLocationId()
                ));
    }

    public UserInformationDbEntity toDbEntity(UserInformation userInformation){
        if(userInformation == null) return null;

        return new UserInformationDbEntity(
                UserInformationId.toUUID(userInformation.getId()),
                userInformation.getName(),
                userInformation.getLastName(),
                userInformation.getSalaryPerWeek(),
                userInformation.getCreatedAt(),
                userInformation.getBirthdate(),
                new UserDbEntity(
                        userInformation.getUser().getId(),
                        userInformation.getUser().getUsername(),
                        userInformation.getUser().getPassword(),
                        userInformation.getUser().getEmail(),
                        userInformation.getUser().getModule(),
                        userInformation.getUser().isFirstTime(),
                        userInformation.getUser().isManager(),
                        userInformation.getUser().isEnabled(),
                        userInformation.getUser().getLocationId()
                ));
    }
}
