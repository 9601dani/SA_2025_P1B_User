package com.danimo.user.user.infrastrcture.inputadapters.rest.dto;

import com.danimo.user.page.domain.Page;
import lombok.Value;

@Value
public class PageResponse {
    private Integer id;
    private String name;
    private String path;

    public static PageResponse fromDomain(Page page) {
        return new PageResponse(
                page.getId(),
                page.getName(),
                page.getPath()
        );
    }
}
