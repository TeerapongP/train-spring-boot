package com.ss.training.repository;

import com.ss.training.domain.TransactionNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionNewRepository extends JpaRepository<TransactionNew,Long> {
	
}