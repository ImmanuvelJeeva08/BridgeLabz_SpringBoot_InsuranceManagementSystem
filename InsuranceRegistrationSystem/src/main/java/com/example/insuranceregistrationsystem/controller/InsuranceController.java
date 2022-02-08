package com.example.insuranceregistrationsystem.controller;

import com.example.insuranceregistrationsystem.dto.InsuranceDTO;
import com.example.insuranceregistrationsystem.dto.ResponseDTO;
import com.example.insuranceregistrationsystem.entity.Insurance;
import com.example.insuranceregistrationsystem.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/insuranceSystem")
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

    /***************************************************************************************************************************
     * Ability to add New Insurance company details to database
     * @param insuranceDTO
     * @return addInsurance
     ***************************************************************************************************************************/

    @PostMapping(value = "/insurance")
    public ResponseEntity<ResponseDTO> addInsuranceDetails(@Valid @RequestBody InsuranceDTO insuranceDTO){
        insuranceDTO.setInsuranceId(insuranceService.idGenerator());
        InsuranceDTO addInsurance = insuranceService.addInsurance(insuranceDTO);
        ResponseDTO responseDTO = new ResponseDTO(addInsurance,"Given InsuranceDetails was Sucessfully Added");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to add New Insurance company details to database
     * @return insuranceDTOList
     ***************************************************************************************************************************/

    @GetMapping(value = "/allInsurance")
    public ResponseEntity<ResponseDTO> getAllInsuranceDetails(){
        List<Insurance> insuranceDTOList = insuranceService.getAllInsurance();
        ResponseDTO responseDTO = new ResponseDTO(insuranceDTOList,"Fetched all Insurance Details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to edit existing Insurance company details in database
     * @param insuranceDTO
     * @return editInsurance
     ***************************************************************************************************************************/

    @PutMapping(value = "/insurance")
    public ResponseEntity<ResponseDTO> editInsuranceDetails(@Valid @RequestBody InsuranceDTO insuranceDTO){
        Insurance editInsurance = insuranceService.editInsurance(insuranceDTO.getInsuranceId(),insuranceDTO);
        ResponseDTO responseDTO = new ResponseDTO(editInsurance,"Sucessfully Updated given Details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to delete Insurance company details from database
     * @param id
     * @return null
     ***************************************************************************************************************************/

    @DeleteMapping(value = "/insurance")
    public ResponseEntity<ResponseDTO> deleteInsuranceDetails(@RequestParam(name = "id") int id){
        insuranceService.deleteInsuranceById(id);
        ResponseDTO responseDTO = new ResponseDTO(null,"Sucessfully Deleted");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to get Specific Insurance company details from database using ID
     * @param id
     * @return insurance
     ***************************************************************************************************************************/

    @GetMapping(value = "/insuranceById")
    public ResponseEntity<ResponseDTO> getInsuranceDetailsById(@RequestParam(name = "id") int id){
        Insurance insurance = insuranceService.getInsuranceById(id);
        ResponseDTO responseDTO = new ResponseDTO(insurance,"Sucessfully Fetched Given Id details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
