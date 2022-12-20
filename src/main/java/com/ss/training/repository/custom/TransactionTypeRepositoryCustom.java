package com.ss.training.repository.custom;

import com.ss.training.domain.TransactionType;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Connection;


@Repository
public class TransactionTypeRepositoryCustom {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
	private EntityManager em;    

    public List<Map<String,Object>> findTransactionTypeByCodeLike(String code){
        LOGGER.info("findTransactionTypeByCodeLike  : {}",code);
        List<Map<String,Object>>  result = new ArrayList<>();
        try{
            StringBuilder sqlStatment = new StringBuilder();
            sqlStatment.append("SELECT id,code,description FROM M_TRANSACTION_TYPE where code like :code order by code ") ;       
            LOGGER.debug("statment : {} ",sqlStatment);
            Query query = em.createNativeQuery(sqlStatment.toString());
            query.setParameter("code","%"+code+"%");
            // List<TransactionType> testList =query.getResultList();
            List<Object[]> listfromQuery=query.getResultList();
            for(Object [] o :listfromQuery ){
                Map data = new HashMap<>();
                data.put("id",o[0]);
                data.put("code",o[1]);
                data.put("description",o[2]);
                result.add(data);
            }
        }catch(Exception e){
            LOGGER.error("findTransactionTypeByCodeLike error : {}",e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public List<TransactionType> fileterTransactionTypeByCode(String code){
        LOGGER.info("fileterTransactionTypeByCode  : {}",code);
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<TransactionType> cq = cb.createQuery(TransactionType.class);
            Root<TransactionType> transactionTypeRoot = cq.from(TransactionType.class);
            List<Predicate> predicates = new ArrayList<>(); 
            if(code != null){
                predicates.add(cb.like(transactionTypeRoot.get("code"),"%"+code+"%"));
            }
            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(transactionTypeRoot.get("code")));
            TypedQuery<TransactionType> query = em.createQuery(cq);
            return query.getResultList();
        }catch(Exception e ){
            LOGGER.error("fileterTransactionTypeByCode error : {}",e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public void callInsertActivityLog(String parameter1 , String parameter2){
        LOGGER.info("callInsertActivityLog : {} :{} ",parameter1,parameter2);
        try{
            StoredProcedureQuery storedProcedureParameter = em.createStoredProcedureQuery("INSERT_ACTIVITY_LOG");
            storedProcedureParameter.registerStoredProcedureParameter("P_ACTIVITY", String.class, ParameterMode.IN);
            storedProcedureParameter.registerStoredProcedureParameter("P_ACTION_USER", String.class, ParameterMode.IN);
            storedProcedureParameter.setParameter("P_ACTIVITY",parameter1);
            storedProcedureParameter.setParameter("P_ACTION_USER",parameter2);
            storedProcedureParameter.execute();
        }catch(Exception e ){
            LOGGER.error("callInsertActivityLog error : {}",e);
            throw new RuntimeException(e.getMessage());
        }
    } 

    public Integer plusNumber(Integer number1 , Integer number2){
        LOGGER.info("plusNumber : {} :{} ",number1,number2);
        try{
            Session session = em.unwrap(Session.class);
            Integer result = null;
            result = session.doReturningWork(new ReturningWork<Integer>() {
                @Override
                public Integer execute(Connection connection) throws SQLException{
                    CallableStatement cStmt =  null;
                    Integer returnValue = null;
                    try{
                        cStmt = connection.prepareCall("{? = call PLUS_NUMBER(?,?) }");
                        cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                        cStmt.setInt(2, number1);
                        cStmt.setInt(3, number2);
                        cStmt.executeUpdate();
                        returnValue = cStmt.getInt(1);
                        LOGGER.debug("plusNumber result :{} ",returnValue);
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }finally{
                        if(cStmt!=null){
                            cStmt.close();
                        }
                    }
                    return returnValue;
                }
            });
            return result;
        }catch(Exception e){
            LOGGER.error("plusNumber error : {}",e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
