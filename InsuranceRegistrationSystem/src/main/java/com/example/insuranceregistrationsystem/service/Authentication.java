package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.UserLoginDTO;
import com.example.insuranceregistrationsystem.entity.DAOUser;

public interface Authentication {
     void authenticate(String username, String password);
     DAOUser registerUser(UserLoginDTO user);
}
