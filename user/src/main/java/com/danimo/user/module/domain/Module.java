package com.danimo.user.module.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import com.danimo.user.page.domain.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@DomainEntity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    private Integer id;
    private String name;
    private String direction;
    private String icon;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
}
