package com.danimo.user.user.infrastrcture.outputadapters.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDbEntity {

    @Id
    private UUID id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String module;
    @Column
    private boolean firstTime;
    @Column
    private boolean manager;
    @Column
    private boolean enabled;

}
