package com.ss.training.controller;


import com.ss.training.domain.TransactionType;
import com.ss.training.service.TransactionTypeService;
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


@RestController
@RequestMapping("/api/transactiontypes")
public class TransactionTypeController {
	
	@Autowired
	TransactionTypeService transactionTypeService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/findall")
	ResponseEntity<String> findAll(){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			List<TransactionType> result = transactionTypeService.findAll();
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

	@GetMapping("/findById")
	ResponseEntity<String> findById(@RequestParam(value = "id",required = false)Long id){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
		headers.add("errorStatus", "0");
		try{
			TransactionType transacionType = transactionTypeService.findById(id);
            return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").deepSerialize(transacionType), headers, HttpStatus.OK);
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
            TransactionType transactionType = new JSONDeserializer<TransactionType>().use(null, TransactionType.class).deserialize(json);
			if(transactionType.getId() != null){
				transactionTypeService.updateTransactionType(transactionType);
			}else{
				transactionTypeService.insertTransactionType(transactionType);
			}
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

