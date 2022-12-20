package com.ss.training.service.impl;

import  com.ss.training.domain.TransactionType;
import  com.ss.training.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import org.springframework.stereotype.Service;
import  com.ss.training.repository.*;
import  com.ss.training.repository.custom.TransactionHeaderRepositoryCustom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TransactionHeaderRepositoryCustom transactionHeaderRepositoryCustom;

	@Autowired
	TransactionTypeRepository transactionTypeRepository;

	@Override
	public List<TransactionType> findAll(){
		try{
			LOGGER.info("[findAll]");
			return transactionTypeRepository.findAll();
		}catch(Exception e){
			LOGGER.error("[findAll] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public TransactionType findById(Long id){
		try{
			LOGGER.info("[findById] :{}",id);
			Optional<TransactionType> data = transactionTypeRepository.findById(id);
			if (data.isPresent()) {
				LOGGER.debug("[findById] code :{}",data.get().getCode());
			    return data.get();
			} else {
			    return null;
			}
		}catch(Exception e){
			LOGGER.error("[findById] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void insertTransactionType(TransactionType transactionType){
		try{
			LOGGER.info("[insertTransactionType] code:{} description:{}",transactionType.getCode() ,transactionType.getDescription() );
			transactionTypeRepository.save(transactionType);
		}catch(Exception e){
			LOGGER.error("[insertTransactionType] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void updateTransactionType(TransactionType transactionType){
		try{
			LOGGER.info("[updateTransactionType] id:{} code:{} description:{}",transactionType.getId(),transactionType.getCode() ,transactionType.getDescription() );
			TransactionType transactionTypeReal =  findById(transactionType.getId());
			if(transactionType.getVersion() == null) throw new RuntimeException("not found version");
			if(transactionType.getVersion().compareTo(transactionTypeReal.getVersion()) != 0) throw new RuntimeException("Old version can't update");
			transactionTypeReal.setCode(transactionType.getCode());
			transactionTypeReal.setDescription(transactionType.getDescription());
			transactionTypeRepository.save(transactionTypeReal);
		}catch(Exception e){
			LOGGER.error("[updateTransactionType] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void deleteTransactionType(List<Long> idList){
		LOGGER.info("[deleteTransactionType]  : {} ",idList);
		try{
			 // data = 1,2,3,4,5,6 ,7
			 // case 1-3 success  4 fail  delete 1,2,3
			 //  if fail and throw exception all data is rollout A
			for(Long id: idList){
				LOGGER.debug("[deleteTransactionType] delete id :{}  ",id);
				TransactionType transactionTypeReal =  findById(id);
				if(transactionTypeReal!= null){
					transactionTypeRepository.delete(transactionTypeReal);
					// transactionTypeRepository.flush();
					// call package
				}else{
					LOGGER.debug("[deleteTransactionType] not found id :{}  ",id);
				}
			}
		}catch(Exception e){
			LOGGER.error("[updateTransactionType] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	@Transactional
	public void callStoreProcedure(String  message,String actionUser){
			LOGGER.info("[callStoreProcedure]  : {} :{} ",message,actionUser);
		try{
			 transactionHeaderRepositoryCustom.callStoreProcedure(message,actionUser);		
		}catch(Exception e ){
			LOGGER.error("[callStoreProcedure] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public  Integer callStoreFunction(Integer num1,Integer num2){
		LOGGER.info("[callStoreFunction]  : {} :{} ",num1,num2);
		try{
			return transactionHeaderRepositoryCustom.callStoreFunction(num1,num2);		
		}catch(Exception e ){
			LOGGER.error("[callStoreFunction] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TransactionType> filterTransactionType(String code){
		try{
	        return transactionHeaderRepositoryCustom.filterTransactionType(code);
		}catch(Exception e){
			LOGGER.error("[callStoreFunction] errorMsg : {} ",e);
			throw new RuntimeException(e.getMessage());
		}
	}
}   

