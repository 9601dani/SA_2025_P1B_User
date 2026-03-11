package com.danimo.user.page.infrastructure.outputadapters.persistence.entity;

import com.danimo.user.module.infrastructure.outputadapters.persistence.entity.ModuleDbEntity;
import com.danimo.user.role.infrastructure.outputadapters.persistence.entity.RoleDbEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "page")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String path;
    @Column(length = 100)
    private String icon;

    @Column(name = "show_in_menu", columnDefinition = "TINYINT")
    private Boolean showInMenu;

    @ManyToOne
    @JoinColumn(name = "FK_Module")
    private ModuleDbEntity module;

    @Column(columnDefinition = "TINYINT")
    private Boolean isAvailable;

    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_has_page",
            joinColumns = @JoinColumn(name = "FK_page"),
            inverseJoinColumns = @JoinColumn(name = "FK_role")
    )
    private List<RoleDbEntity> roles = new ArrayList<>();
}
