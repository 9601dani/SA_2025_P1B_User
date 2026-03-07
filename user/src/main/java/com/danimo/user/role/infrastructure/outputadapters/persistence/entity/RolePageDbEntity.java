package com.danimo.user.role.infrastructure.outputadapters.persistence.entity;

import com.danimo.user.page.infrastructure.outputadapters.persistence.entity.PageDbEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_has_page")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RolePageDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_role")
    private RoleDbEntity role;

    @ManyToOne
    @JoinColumn(name = "FK_page")
    private PageDbEntity page;

    @Column(columnDefinition = "TINYINT")
    private Boolean canCreate = true;

    @Column(columnDefinition = "TINYINT")
    private Boolean canEdit = true;

    @Column(columnDefinition = "TINYINT")
    private Boolean canDelete = true;
}
