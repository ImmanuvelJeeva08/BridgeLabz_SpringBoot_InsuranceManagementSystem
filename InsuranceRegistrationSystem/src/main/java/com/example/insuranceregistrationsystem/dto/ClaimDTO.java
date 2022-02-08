package com.example.insuranceregistrationsystem.dto;

import com.example.insuranceregistrationsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDTO{
    private int id;
    private User user;
    private int totalAmount;
    private Date claimDate;
    private String status = "Not Paid";
}
