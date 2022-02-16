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
    private String base64image;
    private String filepath;
    private int totalAmount;
    private String reason;
    private Date claimDate;
    private String bill;
    private String cheque;
    private String status = "Not Claimed";

}
