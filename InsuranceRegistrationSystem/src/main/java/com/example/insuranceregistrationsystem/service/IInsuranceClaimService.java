package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.ClaimDTO;
import com.example.insuranceregistrationsystem.entity.ClaimEntity;
import java.util.List;

public interface IInsuranceClaimService {
    public ClaimEntity claimInsurance(ClaimDTO userDTO);
    public List<ClaimEntity> getAllClaimedInsuranceDetails();
    public int idGenerator();
}
