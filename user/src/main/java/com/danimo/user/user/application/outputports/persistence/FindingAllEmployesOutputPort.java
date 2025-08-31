package com.danimo.user.user.application.outputports.persistence;

import com.danimo.user.user.domain.User;

import java.util.List;

public interface FindingAllEmployesOutputPort {
    List<User> findAllEmployes();
}
