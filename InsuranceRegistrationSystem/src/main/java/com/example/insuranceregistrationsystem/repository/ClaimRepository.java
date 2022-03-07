package com.example.insuranceregistrationsystem.repository;

import com.example.insuranceregistrationsystem.entity.ClaimEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends MongoRepository<ClaimEntity, Integer> {
    ClaimEntity findClaimEntityByUser_Email(String email);
}
