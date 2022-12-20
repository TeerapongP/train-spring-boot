package com.ss.training.service;

import com.ss.training.domain.AppUser;

import java.util.List;

public interface AppUserService {
    List<AppUser> findAll();
    AppUser findById(Long id);
    void insertAppUser(AppUser json);
    void updateAppUser(AppUser json);
    void deleteAppUser(List<Long> idList);
}
