package com.example.insuranceregistrationsystem.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ClaimEntity {

    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private int totalAmount;
    private Date claimDate;
    private String status = "Not Paid";
}
