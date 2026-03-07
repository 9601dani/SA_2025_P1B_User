package com.danimo.user.module.infrastructure.outputadapters.persistence.repository;

import com.danimo.user.module.infrastructure.outputadapters.persistence.entity.ModuleDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ModuleDbEntityJpaRepository extends JpaRepository<ModuleDbEntity, Integer> {
}
