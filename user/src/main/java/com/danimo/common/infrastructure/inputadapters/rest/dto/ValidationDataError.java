package com.danimo.common.infrastructure.inputadapters.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ValidationDataError {
    private final List<String> messages;
}
