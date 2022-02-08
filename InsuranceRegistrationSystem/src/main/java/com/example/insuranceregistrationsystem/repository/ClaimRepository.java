package com.example.insuranceregistrationsystem.repository;

import com.example.insuranceregistrationsystem.entity.ClaimEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClaimRepository extends MongoRepository<ClaimEntity, Integer> {
    Optional<ClaimEntity>  findClaimEntityByUser(int id);
}
