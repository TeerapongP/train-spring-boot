package com.ss.training.service;

import  com.ss.training.AbstractApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import org.junit.Assert;

import  com.ss.training.domain.TransactionNew;
import  com.ss.training.domain.TransactionType;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class TransactionNewServiceTest  {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TransactionNewService transactionNewService;

    @Autowired
    TransactionTypeService transactionTypeService;

    @Test
    public void testmethodFindAllTransactionNew(){
        List<TransactionNew> trasnsactionNews = transactionNewService.findAll();
        LOGGER.debug("trasnsactionNews :{} ",trasnsactionNews.size());
    }
   
    @Test
    public void testmethodFindransactionNewById(){
       TransactionNew trasnsactionNew = transactionNewService.findById(1L);
        LOGGER.debug("trasnsactionNew :{} ",trasnsactionNew);
    }
      
    @Test
    public void testmethodFindTransactionTypeByCodeLike(){
        List<Map<String,Object>> result = transactionNewService.findTransactionTypeByCodeLike("00");
        LOGGER.debug("trasnsactionNew :{} ",result.size());
    }

    @Test 
    public void testmethodFileterTransactionTypeByCode(){
        List<TransactionType> result = transactionNewService.fileterTransactionTypeByCode("00");
        LOGGER.debug("trasnsactionNew :{} ",result.size());
    }     
    
    @Test 
    public void testCallInsertActivityLog(){
       transactionNewService.callInsertActivityLog("Training SpringBoot","Nuttachai TI");
       LOGGER.debug("============================ callInsertActivityLog end ====================== ");
        
    }     
    
    @Test 
    public void testPlusNumber(){
       Integer summary =  transactionNewService.plusNumber(99 , 100);
       LOGGER.debug("============================ plusNumber :{} ====================== ",summary);
        
    }    
    
    @Test
    public void testInsertTranasctionType(){
        List<TransactionType> orginResult = transactionTypeService.findAll();
        TransactionType data = new TransactionType();
        data.setCode("111");
        data.setDescription("Junit Test");
        transactionNewService.insertTransactionType(data);
        List<TransactionType> afterInsertResult = transactionTypeService.findAll();
        Assert.assertEquals(orginResult.size()+1, afterInsertResult.size());

    }

    
}



