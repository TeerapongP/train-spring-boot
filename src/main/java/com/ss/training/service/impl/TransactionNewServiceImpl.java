package com.ss.training.service.impl;


import com.ss.training.service.TransactionNewService;

import org.springframework.stereotype.Service;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.training.domain.TransactionNew;
import com.ss.training.domain.TransactionType;
import com.ss.training.repository.TransactionNewRepository;
import com.ss.training.repository.TransactionTypeRepository;
import com.ss.training.repository.custom.TransactionTypeRepositoryCustom;
import java.util.List;
import java.util.Map;

@Service
public class TransactionNewServiceImpl implements TransactionNewService {
 	
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

 	@Autowired
	TransactionNewRepository tranasctionNewRepository; 

	@Autowired
	TransactionTypeRepositoryCustom transactionTypeRepositoryCustom;

	@Autowired
	TransactionTypeRepository transactionTypeRepository;

	@Override
	public List<TransactionNew> findAll(){
		LOGGER.info("findAll");
		try{
			return 	tranasctionNewRepository.findAll();	
		}catch(Exception e){
			LOGGER.error("findAll errro : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public TransactionNew findById(Long id){
		LOGGER.info("findById :{} ",id);
		try{
			TransactionNew transactionNew = null;
			Optional<TransactionNew> optionalTransactionNew = tranasctionNewRepository.findById(id);
			if(optionalTransactionNew.isPresent()){
				transactionNew = optionalTransactionNew.get();
			}
			return transactionNew;
		}catch(Exception e){
			LOGGER.error("findAll errro : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Map<String,Object>> findTransactionTypeByCodeLike(String code){
		LOGGER.info("service findTransactionTypeByCodeLike : {}",code);
		try{
			return transactionTypeRepositoryCustom.findTransactionTypeByCodeLike(code);
		}catch(Exception e){
			LOGGER.error("service findTransactionTypeByCodeLike errro : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public  List<TransactionType> fileterTransactionTypeByCode(String code){
		LOGGER.info("service fileterTransactionTypeByCode : {}",code);
		try{
			return transactionTypeRepositoryCustom.fileterTransactionTypeByCode(code);
		}catch(Exception e){
			LOGGER.error("service fileterTransactionTypeByCode errro : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public  void callInsertActivityLog(String parameter1, String parameter2){
		LOGGER.info("service callInsertActivityLog : {} :{} ",parameter1,parameter2);
		try{
			 transactionTypeRepositoryCustom.callInsertActivityLog(parameter1,parameter2);
		}catch(Exception e){
			LOGGER.error("service callInsertActivityLog errro : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Integer plusNumber(Integer num1, Integer num2){
		LOGGER.info("service plusNumber : {} :{} ",num1,num2);
		try{
			return transactionTypeRepositoryCustom.plusNumber(num1,num2);
		}catch(Exception e){
			LOGGER.error("service plusNumber errro : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override 
	public void insertTransactionType(TransactionType data){
		LOGGER.info("service insertTransactionType : {} :{} ",data.getCode(),data.getDescription());
		try{
			transactionTypeRepository.save(data);
		}catch(Exception e){
			LOGGER.error("service insertTransactionType errro : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

}   

