package com.example.insuranceregistrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private int otp;
}
