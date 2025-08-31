package com.danimo.user.information.infrastructure.outputadapters.persistence.repository;

import com.danimo.user.information.infrastructure.outputadapters.persistence.entity.UserInformationDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInformationEntityJpaRepository extends JpaRepository<UserInformationDbEntity, UUID> {

    Optional<UserInformationDbEntity> findByUser_Id(UUID userId);

    void deleteByUser_Id(UUID userId);
}
