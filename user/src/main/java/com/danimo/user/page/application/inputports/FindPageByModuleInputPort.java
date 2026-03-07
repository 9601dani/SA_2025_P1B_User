package com.danimo.user.page.application.inputports;

import com.danimo.user.page.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FindPageByModuleInputPort {
    List<Page> findByUserId(UUID userId);
}
