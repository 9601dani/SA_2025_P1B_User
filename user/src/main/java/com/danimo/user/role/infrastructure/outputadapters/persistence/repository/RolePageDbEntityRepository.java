package com.danimo.user.role.infrastructure.outputadapters.persistence.repository;

import com.danimo.user.page.infrastructure.outputadapters.persistence.entity.PageDbEntity;
import com.danimo.user.role.infrastructure.outputadapters.persistence.entity.RolePageDbEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RolePageDbEntityRepository extends JpaRepository<RolePageDbEntity, Integer> {
    @Query("""
        SELECT DISTINCT rp.page
        FROM UserDbEntity u
        JOIN u.roles r
        JOIN RolePageDbEntity rp ON rp.role = r
        WHERE u.id = :userId
    """)
    List<PageDbEntity> findPagesByUserId(@Param("userId") UUID userId);
}
