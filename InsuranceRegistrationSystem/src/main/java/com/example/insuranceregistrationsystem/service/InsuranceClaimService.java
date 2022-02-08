package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.entity.ClaimEntity;
import com.example.insuranceregistrationsystem.entity.User;
import com.example.insuranceregistrationsystem.exception.InsuranceException;
import com.example.insuranceregistrationsystem.repository.ClaimRepository;
import com.example.insuranceregistrationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class InsuranceClaimService implements IInsuranceClaimService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    EmailService emailService;

    /******************************************************************************************************************************
     * Ability to verify the claim user details.
     * If verified, then claim the Insurance
     * @param userDTO
     * @return claimEntity
     ****************************************************************************************************************************/

    @Override
    public ClaimEntity claimInsurance(UserDTO userDTO) {
        ClaimEntity claimEntity = new ClaimEntity();
        Optional<User> user = userRepository.findUserByUserFullNameAndMobileNoAndVehicleNo(userDTO.getUserFullName(),userDTO.getMobileNo(),userDTO.getVehicleNo());
        if(user.isPresent()){
            claimEntity.setId(idGenerator());
            claimEntity.setClaimDate(new Date());
            claimEntity.setUser(user.get());
            int amount = user.get().getInsuranceAmountPerMonth() * user.get().getInsuranceYear();
            claimEntity.setTotalAmount(amount);
            claimEntity.setStatus("Paid");
            claimRepository.save(claimEntity);
            return claimEntity;
        }else {
            throw new InsuranceException("User not Found! Enter Correct Details");
        }
    }

    /****************************************************************************************************************************
     * Ability to retrive allClaimedInsurance records from database.
     * @return
     ***************************************************************************************************************************/

    @Override
    public List<ClaimEntity> getAllClaimedInsuranceDetails() {
        return claimRepository.findAll();
    }

    /****************************************************************************************************************************
     * Ability to generate random number for each claimingInsurance.
     * @return result
     ***************************************************************************************************************************/

    @Override
    public int idGenerator() {
        Random r = new Random();
        int low = 100;
        int high = 999;
        int result = r.nextInt(high-low) + low;
        System.out.println("ID = "+result);
        Optional<ClaimEntity> existOrNot = claimRepository.findById(result);
        if(existOrNot.isPresent()){
            idGenerator();
        }
        return result;
    }
}
