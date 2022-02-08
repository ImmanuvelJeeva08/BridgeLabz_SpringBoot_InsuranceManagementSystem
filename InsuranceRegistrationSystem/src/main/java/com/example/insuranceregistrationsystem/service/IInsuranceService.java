package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.InsuranceDTO;
import com.example.insuranceregistrationsystem.entity.Insurance;
import java.util.List;

public interface IInsuranceService {
    public InsuranceDTO addInsurance(InsuranceDTO insuranceDTO);
    public List<Insurance> getAllInsurance();
    public Insurance getInsuranceById(int id);
    public void deleteInsuranceById(int id);
    public Insurance editInsurance(int id, InsuranceDTO insuranceDTO);
    public int idGenerator();
}
