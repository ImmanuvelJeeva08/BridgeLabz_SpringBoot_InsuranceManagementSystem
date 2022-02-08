package com.example.insuranceregistrationsystem.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    private int userId;
    private String userFullName;
    private LocalDate dob;
    private String email;
    private String address;
    private String mobileNo;
    private int insuranceAmountPerMonth;
    private int insuranceYear;
    private String vehicleModel;
    private String vehicleNo;
    private Date registerDate;
    private Date dataModifiedDate;

    @OneToOne
    private Insurance insurance;

}
