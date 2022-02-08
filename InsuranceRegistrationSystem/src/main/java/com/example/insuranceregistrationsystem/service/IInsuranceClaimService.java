package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.entity.ClaimEntity;
import java.util.List;

public interface IInsuranceClaimService {
    public ClaimEntity claimInsurance(UserDTO userDTO);
    public List<ClaimEntity> getAllClaimedInsuranceDetails();
    public int idGenerator();
}
