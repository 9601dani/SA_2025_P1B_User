package com.danimo.user.page.domain;

import com.danimo.user.common.domain.annotations.DomainEntity;
import com.danimo.user.module.domain.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@DomainEntity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private Integer id;
    private String name;
    private String path;
    private Module module;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
}
