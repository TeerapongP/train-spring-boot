package com.ss.training.controller;


import  com.ss.training.domain.TransactionHeader;
import  com.ss.training.service.TransactionHeaderService;
import  com.ss.training.service.TransactionTypeService;
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


@RestController
@RequestMapping("/api/transactionheaders")
public class TransactionHeaderController {
	
	@Autowired
	TransactionHeaderService transactionHeaderService;

	@Autowired
	TransactionTypeService transactionTypeService;




    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	// localhost:8888/training/api/transactionheaders/findById?id=1

	@GetMapping("/findById")
	ResponseEntity<String> findById(@RequestParam(value = "id",required = false)Long id){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			TransactionHeader transactionHeader = transactionHeaderService.findById(id);
            return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").deepSerialize(transactionHeader), headers, HttpStatus.OK);
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

	@PostMapping("/save")
	ResponseEntity<String> save(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
            TransactionHeader transactionHeader = new JSONDeserializer<TransactionHeader>().use(null, TransactionHeader.class).deserialize(json);
			transactionHeaderService.insertTransactionHeader(transactionHeader);
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


	@GetMapping("/callStoreProcedure")
	ResponseEntity<String> callStoreProcedure(@RequestParam(value = "message",required = false)String message,
			@RequestParam(value = "actionUser",required = false)String actionUser
		){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			 transactionTypeService.callStoreProcedure(message,actionUser);
            return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").deepSerialize("success"), headers, HttpStatus.OK);
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

	@GetMapping("/callStoreFunction")
	ResponseEntity<String> callStoreFunction(@RequestParam(value = "num1",required = false)Integer num1,
			@RequestParam(value = "num2",required = false) Integer num2){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			Integer result= transactionTypeService.callStoreFunction(num1,num2);
            return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").deepSerialize(result), headers, HttpStatus.OK);
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

	@GetMapping("/filterTransactionType")
	ResponseEntity<String> filterTransactionType(@RequestParam(value = "code",required = false)String code){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
            return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").deepSerialize(transactionTypeService.filterTransactionType(code)), headers, HttpStatus.OK);
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

