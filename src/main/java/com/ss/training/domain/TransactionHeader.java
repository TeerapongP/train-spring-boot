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
@Table(name = "TA_TRANSACTION_HDR")
public class TransactionHeader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Version
    private Long version;

    private String documentNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Timestamp documentDate;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "TRANSACTION_TYPE")
    TransactionType transactionType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "transactionHeader" , orphanRemoval = true)
    private Set<TransactionDetail> transactionDtls;

}