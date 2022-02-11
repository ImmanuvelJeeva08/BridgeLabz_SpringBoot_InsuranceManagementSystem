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

    @ElementCollection
    private List<String> base64image;

    @ElementCollection
    private List<String> filepath;

    private int totalAmount;
    private Date claimDate;
    private String status = "Not Paid";
}
