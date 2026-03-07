package com.danimo.user.module.infrastructure.outputadapters.persistence.entity;

import com.danimo.user.page.infrastructure.outputadapters.persistence.entity.PageDbEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "module")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String direction;
    @Column(columnDefinition = "TINYINT")
    private Boolean isAvailable;
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    private List<PageDbEntity> pages;
}
