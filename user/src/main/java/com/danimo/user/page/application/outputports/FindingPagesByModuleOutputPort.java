package com.danimo.user.page.application.outputports;

import com.danimo.user.page.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FindingPagesByModuleOutputPort {
    List<Page> findingPagesByUserId(UUID userId);
}
