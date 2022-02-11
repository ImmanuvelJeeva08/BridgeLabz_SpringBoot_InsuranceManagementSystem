package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserService {
    public UserDTO addUser(UserDTO userDTO);
    public List<User> getAllUsers();
    public User getUserById(int id);
    public void deleteUserById(int id);
    public User editUser(int id, UserDTO userDTO);
    public int idGenerator();
    public void resetPassword(String email);
    public void passwordChange(int otp,String password);

    User getUserDetailsByEmail(UserDetails userDetails);
}
