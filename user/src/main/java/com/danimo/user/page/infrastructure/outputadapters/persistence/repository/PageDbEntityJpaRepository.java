package com.danimo.user.page.infrastructure.outputadapters.persistence.repository;

import com.danimo.user.page.infrastructure.outputadapters.persistence.entity.PageDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageDbEntityJpaRepository extends JpaRepository<PageDbEntity, Integer> {
}
