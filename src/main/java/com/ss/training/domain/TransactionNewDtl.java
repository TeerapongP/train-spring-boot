package com.ss.training.domain;

import com.ss.training.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Set;

@Entity
@Data
@Table(name = "Z_TRANSACTION_NEW_DTTL")
public class TransactionNewDtl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Version
    private Long version;

  
    private Integer sequence;
    
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "TRANSACTION_NEW")
    TransactionNew transactionNew;


}