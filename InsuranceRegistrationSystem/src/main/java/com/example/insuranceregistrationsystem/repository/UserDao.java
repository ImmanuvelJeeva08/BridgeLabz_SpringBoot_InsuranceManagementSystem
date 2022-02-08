package com.example.insuranceregistrationsystem.repository;

import com.example.insuranceregistrationsystem.entity.DAOUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends MongoRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);
    DAOUser findByEmail(String email);
}
