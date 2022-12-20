package com.ss.training.repository;

import com.ss.training.domain.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHeaderRepository extends JpaRepository<TransactionHeader,Long> {
	
}