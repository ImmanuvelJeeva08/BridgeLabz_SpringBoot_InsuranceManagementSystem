package com.example.insuranceregistrationsystem.repository;

import com.example.insuranceregistrationsystem.entity.Insurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends MongoRepository<Insurance, Integer> {
    Insurance findByInsuranceId(int id);
}
