package com.ss.training.service;


import com.ss.training.domain.TransactionNew;
import com.ss.training.domain.TransactionType;
import java.util.List;
import java.util.Map;

public interface TransactionNewService {
  List<TransactionNew> findAll();
  TransactionNew findById(Long id);
  List<TransactionType> fileterTransactionTypeByCode(String code);
  void callInsertActivityLog(String parameter1, String parameter2);
  Integer plusNumber(Integer num1, Integer num2);
  List<Map<String,Object>> findTransactionTypeByCodeLike(String code);
  void insertTransactionType(TransactionType data);

}   

