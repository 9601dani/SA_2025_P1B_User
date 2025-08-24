package com.danimo.common.infrastructure.inputadapters.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RestApiError {
    private final int statusCode;
    private final String message;
}
