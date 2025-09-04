package com.danimo.user.user.application.outputports.rest;

import java.util.UUID;

public interface ExistLocationOutputPort {
    boolean existLocation(UUID locationId);
}
