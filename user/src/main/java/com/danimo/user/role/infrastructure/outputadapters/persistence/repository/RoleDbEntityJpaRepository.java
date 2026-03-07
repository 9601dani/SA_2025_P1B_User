package com.danimo.user.role.infrastructure.outputadapters.persistence.repository;

import com.danimo.user.role.infrastructure.outputadapters.persistence.entity.RoleDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDbEntityJpaRepository extends JpaRepository<RoleDbEntity, Integer> {
}
