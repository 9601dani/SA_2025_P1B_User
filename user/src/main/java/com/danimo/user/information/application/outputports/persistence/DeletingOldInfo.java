package com.danimo.user.information.application.outputports.persistence;

import java.util.UUID;

public interface DeletingOldInfo {
    void deleteOldInfoByUserId(UUID userId);
}
