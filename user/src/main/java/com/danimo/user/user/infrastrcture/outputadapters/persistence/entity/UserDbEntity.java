package com.danimo.user.user.infrastrcture.outputadapters.persistence.entity;

import com.danimo.user.role.infrastructure.outputadapters.persistence.entity.RoleDbEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDbEntity {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private boolean firstTime;
    @Column
    private boolean enabled;
    @Column(columnDefinition = "CHAR(36)")
    private UUID locationId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_has_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleDbEntity> roles = new ArrayList<>();

}
