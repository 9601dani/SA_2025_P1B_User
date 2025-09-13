package com.danimo.user.information.infrastructure.outputadapters.persistence.entity;

import com.danimo.user.user.infrastrcture.outputadapters.persistence.entity.UserDbEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "user_information")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationDbEntity {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    private BigDecimal salaryPerWeek;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDate birthdate;
    @OneToOne
    @JoinColumn(name = "FK_User")
    private UserDbEntity user;

}
