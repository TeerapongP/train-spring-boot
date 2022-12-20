package com.ss.training.repository.custom;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import java.math.BigDecimal;	
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import org.hibernate.jdbc.Work; 
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Connection;
import org.hibernate.jdbc.ReturningWork; 
import  com.ss.training.repository.*;
import  com.ss.training.domain.TransactionType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Repository
public class TransactionHeaderRepositoryCustom{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager em;

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    public List<Map<String,Object>> testNativeQuery(Long id){
		LOGGER.info("[testNativeQuery] Param {}",id);
    	List<Object[]> listfromQuery = new ArrayList<Object[]>();
		List<Map<String,Object>> result = new ArrayList<>();
    	try{
			StringBuilder sqlStatement = new StringBuilder();  
			sqlStatement.append(" SELECT ID ,CODE,DESCRIPTION FROM M_TRANSACTION_TYPE WHERE ID = :id ");
			LOGGER.debug("sqlStatement : {} ",sqlStatement);
    		Query query = em.createNativeQuery(sqlStatement.toString());
			query.setParameter("id",id);
			listfromQuery=query.getResultList();
				for(Object [] o :listfromQuery ){
					Map<String,Object> mapResult = new HashMap();
					mapResult.put("id",o[0]);
					mapResult.put("code",o[1]);
					mapResult.put("description",o[2]);
					result.add(mapResult);
			 	}

    	}catch(Exception e){
    		LOGGER.error("[testNativeQuery]error Msg {}",e);
			throw new RuntimeException(e);
    	}
    	return result;
    }


	public void callStoreProcedure(String message,String actionUser){
		LOGGER.info("load_function callStoreProcedure[Service] :{} :{} ",message,actionUser);
       	String result = "" ;
		try{
			StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("INSERT_ACTIVITY_LOG");
	        storedProcedure.registerStoredProcedureParameter("P_ACTIVITY", String.class, ParameterMode.IN);
	        storedProcedure.registerStoredProcedureParameter("P_ACTION_USER", String.class, ParameterMode.IN);
	        storedProcedure.setParameter("P_ACTIVITY", message);
	        storedProcedure.setParameter("P_ACTION_USER", actionUser);
	        storedProcedure.execute();

		}catch(Exception e){
			LOGGER.error("error msg : {}",e.getMessage());
			e.printStackTrace();
		
			throw new RuntimeException(e);
		}
	}


    public Integer callStoreFunction(Integer num1,Integer num2){
        try{

             Session session = em.unwrap(Session.class);
             Integer result = null;
              result = session.doReturningWork(new ReturningWork<Integer>() {
                @Override
                public Integer execute(Connection connection) throws SQLException {
                    CallableStatement cStmt  = null;
                    Integer returnValue = null;
                    try {
                    LOGGER.debug("data : {} :{} ",num1 , num2);
                          cStmt = connection.prepareCall("{? = call PLUS_NUMBER(?,?) }");
                          cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                          cStmt.setInt(2, num1);
                          cStmt.setInt(3, num2);
                          Integer returnResult = null;
                          cStmt.executeUpdate();
                          returnResult = cStmt.getInt(1);

                        LOGGER.debug(" get function Result :  {}",returnResult);
                        returnValue = returnResult;
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }finally {
                        if (cStmt != null) {
                            cStmt.close();
                        }
                    }
                    return returnValue;
                }
            });
           return result;
        }catch(Exception e){
            LOGGER.error("error msg:{}",e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<TransactionType> filterTransactionType(String code){
       LOGGER.info("filterTransactionType : {} ",code);
       try{
          CriteriaBuilder cb = em.getCriteriaBuilder();
          CriteriaQuery<TransactionType> cq = cb.createQuery(TransactionType.class);
          Root<TransactionType> transactionTypeList = cq.from(TransactionType.class);
          List<Predicate> predicates = new ArrayList<>();
           if (code != null) {
                predicates.add(cb.like(transactionTypeList.get("code"), "%"+code+"%"));
            }
            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(transactionTypeList.get("code")));
           TypedQuery<TransactionType> query = em.createQuery(cq);
           return query.getResultList();
        }catch(Exception e){
            LOGGER.error("error msg:{}",e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    } 

 

	
}