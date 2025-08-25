package com.danimo.user.user.infrastrcture.outputadapters.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.websocket.server.ServerEndpoint;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDbEntity {

    @Id
    private UUID id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
}
