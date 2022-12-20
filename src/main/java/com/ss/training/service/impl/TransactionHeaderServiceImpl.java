package com.ss.training.service.impl;

import  com.ss.training.domain.TransactionHeader;
import  com.ss.training.domain.TransactionType;
import  com.ss.training.domain.TransactionDetail;
import  com.ss.training.service.TransactionHeaderService;
import  com.ss.training.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Service;
import  com.ss.training.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionHeaderServiceImpl implements TransactionHeaderService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TransactionHeaderRepository transactionHeaderRepository;

	@Autowired
	TransactionTypeService transactionTypeService;


	@Override
	public TransactionHeader findById(Long id){
		LOGGER.info("[findById]  : {} ",id);
		try{
			Optional<TransactionHeader> data = transactionHeaderRepository.findById(id);
			if (data.isPresent()) {
				LOGGER.debug("[findById] code :{}",data.get().getDocumentNumber());
			    return data.get();
			} else {
			    return null;
			}
		}catch(Exception e){
			LOGGER.error("[findById] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}
  
   @Transactional
   @Override
   public void insertTransactionHeader(TransactionHeader transactionHeaer){
  		try{
			LOGGER.info("[insertTransactionHeader] number:{} ",transactionHeaer.getDocumentNumber()  );
			// {   "id":1,
			//     "createdBy":"postman",
			//     "documentNumber":"TEST001",
			//     "documentDate":1586707200000,
			//     "transactionType":{"id":100002},
			//     "transactionDtls":[{"sequence":1,"amount":"100","transactionHeader":1},{"sequence":2,"amount":"200","transactionHeader":1}]
			// }

			Long transactionTypeId = transactionHeaer.getTransactionType().getId();
			TransactionType transactionType = transactionTypeService.findById(transactionTypeId);
			transactionHeaer.setTransactionType(transactionType);
			Set<TransactionDetail> dtls = transactionHeaer.getTransactionDtls();
			Set<TransactionDetail> child = new HashSet<>(); 
			for(TransactionDetail dtl : dtls){
				dtl.setTransactionHeader(transactionHeaer);
				child.add(dtl);
			}
			transactionHeaer.setTransactionDtls(child);
			transactionHeaderRepository.save(transactionHeaer);
		}catch(Exception e){
			LOGGER.error("[insertTransactionType] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
  }

	

}   

