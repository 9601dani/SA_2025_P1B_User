package com.danimo.user.infrastrcture.outputadapters.persistence.repository;

import com.danimo.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDbEntityJpaRepository extends JpaRepository<UserDbEntity, UUID> {

    Optional<UserDbEntity> findByUsername(String username);
}
