package com.danimo.user.role.infrastructure.outputadapters.persistence.entity;

import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "roles")
    private List<UserDbEntity> users = new ArrayList<>();


}
