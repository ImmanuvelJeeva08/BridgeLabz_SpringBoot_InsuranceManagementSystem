package com.example.insuranceregistrationsystem.dto;

import com.example.insuranceregistrationsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private String jwttoken;
    private User loginUser;
}
