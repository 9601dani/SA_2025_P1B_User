package com.danimo.user.page.infrastructure.outputadapters.persistence.repository;

import com.danimo.user.page.infrastructure.outputadapters.persistence.entity.PageDbEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PageDbEntityJpaRepository extends JpaRepository<PageDbEntity, Integer> {
    Optional<PageDbEntity> findByModuleId(Integer moduleId);

    @Query("""
        SELECT DISTINCT p
        FROM UserDbEntity u
        JOIN u.roles r
        JOIN PageDbEntity p
        JOIN p.roles pr
        WHERE u.id = :userId
          AND pr = r
    """)
    List<PageDbEntity> findPagesByUserId(@Param("userId") UUID userId);
}
