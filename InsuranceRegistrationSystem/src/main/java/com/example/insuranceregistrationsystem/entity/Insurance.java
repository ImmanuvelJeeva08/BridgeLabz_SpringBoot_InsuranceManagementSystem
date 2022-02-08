package com.example.insuranceregistrationsystem.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Insurance {

    @Id
    private int insuranceId;

    private String insuranceCompanyName;
    private String companyAddress;
    private String companyMobile;

}
