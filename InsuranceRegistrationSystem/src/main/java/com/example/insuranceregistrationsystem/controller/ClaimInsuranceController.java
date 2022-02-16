package com.example.insuranceregistrationsystem.controller;

import com.example.insuranceregistrationsystem.dto.ClaimDTO;
import com.example.insuranceregistrationsystem.dto.ResponseDTO;
import com.example.insuranceregistrationsystem.entity.ClaimEntity;
import com.example.insuranceregistrationsystem.service.InsuranceClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/claimInsuranceSystem")
public class ClaimInsuranceController {

    @Autowired
    InsuranceClaimService insuranceClaimService;

    /**************************************************************************************************************************
     * Ablity to add claimInsurance due to vehicle damage
     * @param claimDTO
     * @return adduser
     **************************************************************************************************************************/

    @PostMapping(value = "/claimInsurance")
    public ResponseEntity<ResponseDTO> addClaimDetails(@RequestBody ClaimDTO claimDTO){
        System.out.println(claimDTO);
        ClaimEntity addUser = insuranceClaimService.claimInsurance(claimDTO);
        ResponseDTO responseDTO = new ResponseDTO(addUser, "Insurance was sucessfully Claimed");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**************************************************************************************************************************
     * Ablity to get All claimedInsurance till Now.
     * @return userDTOList
     **************************************************************************************************************************/

    @GetMapping(value = "/AllclaimedInsurance")
    public ResponseEntity<ResponseDTO> getAllClaimedDetails(){
        List<ClaimEntity> userDTOList = insuranceClaimService.getAllClaimedInsuranceDetails();
        ResponseDTO responseDTO = new ResponseDTO(userDTOList,"Fetched "+userDTOList.size()+"Claimed User Details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
