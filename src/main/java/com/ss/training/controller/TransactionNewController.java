package com.ss.training.controller;



import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import  com.ss.training.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import flexjson.JSONDeserializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import flexjson.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import  com.ss.training.domain.TransactionType;

import  com.ss.training.service.TransactionNewService;

@RestController
@RequestMapping("/api/transactionnews")
public class TransactionNewController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TransactionNewService transactionTypeNewService;
  // Integer plusNumber(Integer num1, Integer num2);
  // List<Map<String,Object>> findTransactionTypeByCodeLike(String code);
  // void insertTransactionType(TransactionType data);

	@GetMapping("/plusnumber")
	ResponseEntity<String> plusNumber(
		@RequestParam(value = "number1",required = false)Integer number1,
		@RequestParam(value = "number2",required = false)Integer number2
		){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			Integer result = transactionTypeNewService.plusNumber(number1,number2);
           
            return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
            	.deepSerialize(result), headers, HttpStatus.OK);
		}catch(Exception ex){
			LOGGER.error("Exception : {}",ex);
            headers.add("errorStatus", "-1");
            headers.add("errMsg", ex.getMessage());
            Map<String,Object> result = new HashMap<>();
            result.put("errMsg", ex.getMessage());
            result.put("errorStatus", "-1");
            result.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize(result), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findTransactionTypeByCodeLike")
	ResponseEntity<String> findTransactionTypeByCodeLike(
		@RequestParam(value = "code",required = false)String code
		){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			List<Map<String,Object>> result = transactionTypeNewService.findTransactionTypeByCodeLike(code);
            return new ResponseEntity<String>(new JSONSerializer().exclude("*.class")
            	.deepSerialize(result), headers, HttpStatus.OK);
		}catch(Exception ex){
			LOGGER.error("Exception : {}",ex);
            headers.add("errorStatus", "-1");
            headers.add("errMsg", ex.getMessage());
            Map<String,Object> result = new HashMap<>();
            result.put("errMsg", ex.getMessage());
            result.put("errorStatus", "-1");
            result.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize(result), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
 

	@PostMapping("/savetransactiontype")
	ResponseEntity<String> savetransactiontype(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			LOGGER.info("savetransactiontype :{} ",json);
            TransactionType transactionType = new JSONDeserializer<TransactionType>().use(null, TransactionType.class).deserialize(json);
//			LOGGER.info("getIsActive :{} ",transactionType.getIsActive());
			transactionTypeNewService.insertTransactionType(transactionType);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize("sucess"), headers, HttpStatus.OK);
		}catch(Exception ex){
			LOGGER.error("Exception : {}",ex);
            headers.add("errorStatus", "-1");
            headers.add("errMsg", ex.getMessage());
            Map<String,Object> result = new HashMap<>();
            result.put("errMsg", ex.getMessage());
            result.put("errorStatus", "-1");
            result.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize(result), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}




}   

