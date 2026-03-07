package com.danimo.user.user.infrastrcture.outputadapters.persistence.entity;

import com.danimo.user.role.infrastructure.outputadapters.persistence.entity.RoleDbEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_has_role")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDbEntity userId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleDbEntity roleId;
}
