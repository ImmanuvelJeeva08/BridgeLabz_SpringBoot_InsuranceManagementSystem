package com.example.insuranceregistrationsystem.repository;

import com.example.insuranceregistrationsystem.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findUserByUserFullNameAndMobileNoAndVehicleNo(String userName, String mobileNo, String vehicleNo);
    User findUserByEmail(String email);
    User findUserByUserId(int id);
    User deleteByUserId(int id);
}
