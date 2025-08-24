package com.danimo.user.infrastrcture.outputadapters.persistence;

import com.danimo.common.infrastructure.annotations.PersistenceAdapter;
import com.danimo.user.application.outputports.persistence.FindingUserByUsernameOutputPort;
import com.danimo.user.domain.User;
import com.danimo.user.infrastrcture.outputadapters.persistence.entity.mapper.UserPersistenceMapper;
import com.danimo.user.infrastrcture.outputadapters.persistence.repository.UserDbEntityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@PersistenceAdapter
public class UserRepositoryOutputAdapter implements FindingUserByUsernameOutputPort {

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
}
