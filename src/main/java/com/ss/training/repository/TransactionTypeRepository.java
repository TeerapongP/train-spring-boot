package com.ss.training.repository;

import com.ss.training.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType,Long> {
}