package com.danimo.user.module.application.inputports;

import com.danimo.user.page.domain.Page;

import java.util.List;
import java.util.UUID;

public interface GetModulesByUserIdInputPort {
    List<Page> getPagesByUserId(UUID userId);
}
