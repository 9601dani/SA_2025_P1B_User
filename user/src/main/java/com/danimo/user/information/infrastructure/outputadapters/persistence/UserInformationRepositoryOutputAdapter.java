package com.danimo.user.information.infrastructure.outputadapters.persistence;

import com.danimo.user.common.infrastructure.annotations.PersistenceAdapter;
import com.danimo.user.information.application.outputports.persistence.FindingUserInformationOutputPort;
import com.danimo.user.information.application.outputports.persistence.StoringUserInformationOutputPort;
import com.danimo.user.information.domain.UserInformation;
import com.danimo.user.information.infrastructure.outputadapters.persistence.entity.UserInformationDbEntity;
import com.danimo.user.information.infrastructure.outputadapters.persistence.entity.mapper.UserInformationPersistenceMapper;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.mapper.UserPersistenceMapper;
import com.danimo.user.information.infrastructure.outputadapters.persistence.repository.UserInformationEntityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@PersistenceAdapter
public class UserInformationRepositoryOutputAdapter implements StoringUserInformationOutputPort, FindingUserInformationOutputPort {

    private final UserInformationEntityJpaRepository userInformationEntityJpaRepository;
    private final UserInformationPersistenceMapper userInformationPersistenceMapper;

    @Autowired
    public UserInformationRepositoryOutputAdapter(UserInformationEntityJpaRepository userInformationEntityJpaRepository,
                                                  UserInformationPersistenceMapper userInformationPersistenceMapper, UserPersistenceMapper userPersistenceMapper) {
        this.userInformationEntityJpaRepository = userInformationEntityJpaRepository;
        this.userInformationPersistenceMapper = userInformationPersistenceMapper;
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public UserInformation save(UserInformation userInformation) {
        UserInformationDbEntity savedUserInformation = userInformationEntityJpaRepository.save(userInformationPersistenceMapper.toDbEntity(userInformation));

        return userInformationPersistenceMapper.toDomain(savedUserInformation);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInformation> findUserInformationByUserId(UUID userId) {
        return userInformationEntityJpaRepository.findByUser_Id(userId)
                .map(userInformationPersistenceMapper::toDomain);
    }
}
