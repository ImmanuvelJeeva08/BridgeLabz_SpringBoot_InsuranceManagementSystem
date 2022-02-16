package com.example.insuranceregistrationsystem.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class ClaimEntity {

    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private String base64image;
    private String filepath;
    private int totalAmount;
    private Date claimDate;
    private String reason;
    private String bill;
    private String cheque;
    private String status = "Not Claimed";
}
