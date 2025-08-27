package com.danimo.user.user.infrastrcture.outputadapters.persistence;

import com.danimo.user.common.infrastructure.annotations.PersistenceAdapter;
import com.danimo.user.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.user.application.outputports.persistence.StoringUserOutputPort;
import com.danimo.user.user.domain.User;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.mapper.UserPersistenceMapper;
import com.danimo.user.user.infrastrcture.outputadapters.persistence.repository.UserDbEntityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@PersistenceAdapter
public class UserRepositoryOutputAdapter implements FindingUserByUsernameOutputPort, StoringUserOutputPort {

    private final UserDbEntityJpaRepository userDbEntityJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Autowired
    public UserRepositoryOutputAdapter(UserDbEntityJpaRepository userDbEntityJpaRepository,
                                       UserPersistenceMapper userPersistenceMapper) {
        this.userDbEntityJpaRepository = userDbEntityJpaRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userDbEntityJpaRepository.findByUsername(username)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDbEntityJpaRepository.findByEmail(email)
                .map(userPersistenceMapper::toDomain);
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public User save(User user) {
        UserDbEntity savedUserDbEntity = userDbEntityJpaRepository.save(userPersistenceMapper.toDbEntity(user));

        return userPersistenceMapper.toDomain(savedUserDbEntity);
    }
}
