package com.ss.training.repository;

import com.ss.training.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

}
