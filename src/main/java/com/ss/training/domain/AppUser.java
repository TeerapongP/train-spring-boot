package com.ss.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ss.training.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "APP_USER")
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    private String empCode;

    private String firstName;

    private String lastName;

    private String password;

    private String title;

    private String username;

    @JsonIgnore
    @Version
    private Long version;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ouCompany")
//    private OuCompany ouCompany;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ouCompanyDepartment")
//    private OuCompanyDepartment ouCompanyDepartment;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ouCompanyDepartmentTeam")
//    private OuCompanyDepartmentTeam ouCompanyDepartmentTeam;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userPosition")
//    private UserPosition userPosition;
}
