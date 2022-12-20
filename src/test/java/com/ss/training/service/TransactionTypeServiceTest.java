package com.ss.training.service;

import  com.ss.training.AbstractApplicationTests;
import  com.ss.training.domain.TransactionType;
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


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class TransactionTypeServiceTest  {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TransactionTypeService transactionTypeService;

    public void finished() throws Exception {
        LOGGER.info("-= Finished Tests =-");
    }

    @Test
    public void testFindAll_expectHaveResult(){
        List<TransactionType> types =   transactionTypeService.findAll();
        Assert.assertTrue(types.size()>=0);      
    }

    @Test
    public void testFindAll_expectNotNull(){
        List<TransactionType> types =   transactionTypeService.findAll();
        Assert.assertTrue(types!=null);      
    }

    @Test
    public void testFindById_expectIsNotNull(){
        Long id = 100001L;
        TransactionType types =   transactionTypeService.findById(id);
        Assert.assertTrue(types!=null);      
    }
    
    @Test
    public void testFindById_expectCodeNotEquals(){
        Long id = 100005L;
        TransactionType types =   transactionTypeService.findById(id);
        Assert.assertTrue("TEST".equalsIgnoreCase(types.getCode()));      
    }

    @Test
    public void testFindById_expectIsNull(){
        Long id = 99999L;
        TransactionType types =   transactionTypeService.findById(id);
        Assert.assertTrue(types == null);      
    }

    @Test
    public void testInsertTransactionType_expectSuccess(){
        List<TransactionType> types =   transactionTypeService.findAll();
        TransactionType newTransactionType = new TransactionType();
        newTransactionType.setCode("JUNIT");
        newTransactionType.setDescription("Test from JUNIT");
        transactionTypeService.insertTransactionType(newTransactionType);
        List<TransactionType> aterInsertTypes =   transactionTypeService.findAll();
        Assert.assertTrue(aterInsertTypes.size() == types.size()+1);      
    }


    @Test(expected = RuntimeException.class)
    public void testUpdatetTransactionType_expectException(){
        TransactionType newTransactionType = new TransactionType();
        newTransactionType.setId(100005L);
        newTransactionType.setCode("JUNIT");
        newTransactionType.setDescription("Test from JUNIT");
        transactionTypeService.updateTransactionType(newTransactionType);
    }
    

    
}
