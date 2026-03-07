package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;

import com.danimo.user.module.domain.Module;
import lombok.Value;

import java.util.List;

@Value
public class ModuleResponse {
    private Integer id;
    private String name;
    private String direction;
    private Boolean isAvailable;
    private List<PageResponse> pages;

    public static ModuleResponse fromDomain(Module module, List<PageResponse> pages) {
        return new ModuleResponse(
                module.getId(),
                module.getName(),
                module.getDirection(),
                module.getIsAvailable(),
                pages
        );
    }
}
