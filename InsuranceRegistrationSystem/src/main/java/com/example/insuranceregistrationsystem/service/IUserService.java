package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.entity.User;
import java.util.List;

public interface IUserService {
    public UserDTO addUser(UserDTO userDTO);
    public List<User> getAllUsers();
    public User getUserById(int id);
    public void deleteUserById(int id);
    public User editUser(int id, UserDTO userDTO);
    public int idGenerator();
    void verifyOtpNumber(int otp);
    public void resetPassword(String email);
    public void passwordChange(int otp,String password);
}
