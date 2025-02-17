package com.ss.training.service;


import java.util.List;

import com.ss.training.domain.TransactionType;


public interface TransactionTypeService {
  List<TransactionType> findAll();
  TransactionType findById(Long id);
  void insertTransactionType(TransactionType json);
  void updateTransactionType(TransactionType json);
  void deleteTransactionType(List<Long> idList);
  void callStoreProcedure(String message,String actionBy);
  Integer callStoreFunction(Integer num1,Integer num2);
  List<TransactionType> filterTransactionType(String code);
}   

