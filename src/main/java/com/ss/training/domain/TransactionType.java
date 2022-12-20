package com.ss.training.domain;

import com.ss.training.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.*;


@Entity
@Data
@Table(name = "M_TRANSACTION_TYPE",indexes = @Index(name = "idx_transaction_type1",columnList = "code,description"))
public class TransactionType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Version
    @Column(precision=19, scale=0)
    private Long version;
    
    @Column(length = 10,nullable = false)
    private String code;

    @Column(length = 255,nullable = false)
    private String description;

}