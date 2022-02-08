package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.InsuranceDTO;
import com.example.insuranceregistrationsystem.entity.Insurance;
import com.example.insuranceregistrationsystem.exception.InsuranceException;
import com.example.insuranceregistrationsystem.repository.InsuranceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class InsuranceService implements IInsuranceService{

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private ModelMapper modelMapper;

    /****************************************************************************************************************************
     * Ability to add a new insuranceCompany details in database.
     * @param insuranceDTO
     * @return insuranceDTO
     ***************************************************************************************************************************/

    @Override
    public InsuranceDTO addInsurance(InsuranceDTO insuranceDTO) {
        Insurance addInsurance = modelMapper.map(insuranceDTO, Insurance.class);
        insuranceRepository.save(addInsurance);
        return insuranceDTO;
    }

    /****************************************************************************************************************************
     * Ability to retrive all insuranceCompanyDetails from database.
     * @return allInsurance
     ***************************************************************************************************************************/

    @Override
    public List<Insurance> getAllInsurance() {
        List<Insurance> allInsurance = insuranceRepository.findAll();
        return allInsurance;
    }

    /****************************************************************************************************************************
     * Ability to retrive insuranceCompanyDetails by companyId from database.
     * If not found, throw an Exception.
     ***************************************************************************************************************************/

    @Override
    public Insurance getInsuranceById(int id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new InsuranceException("Unable to find Requested Insurance detail!"));
    }

    /**************************************************************************************************************************
     * Ability to delete the insurance records in database by InsuranceCompany id.
     * @param id
     **************************************************************************************************************************/

    @Override
    public void deleteInsuranceById(int id) {
        if(id > 0) {
            Insurance deleteInsurance = getInsuranceById(id);
            insuranceRepository.delete(deleteInsurance);
        }
    }

    /**************************************************************************************************************************
     * Ability to retrive insuranceCompany details from database by InsuranceCompanyId.
     * Ability to edit the existing records in database and then add it to database.
     * @param id
     * @param insuranceDTO
     * @return editDetails
     **************************************************************************************************************************/

    @Override
    public Insurance editInsurance(int id,InsuranceDTO insuranceDTO) {
        Insurance editDetails = null;
        if (id > 0){
            editDetails = getInsuranceById(id);
            String[] ignoreFields = { "id" };
            BeanUtils.copyProperties(insuranceDTO,editDetails,ignoreFields);
            System.out.println(insuranceDTO);
            System.out.println(editDetails);
            insuranceRepository.save(editDetails);
        }
        return editDetails;
    }

    /******************************************************************************************************************************
     * Generating random id for when adding new Insurance company details
     * @return result
     *****************************************************************************************************************************/

    @Override
    public int idGenerator() {
        Random r = new Random();
        int low = 100;
        int high = 999;
        int result = r.nextInt(high-low) + low;
        System.out.println("ID = "+result);
        Optional<Insurance> existOrNot = insuranceRepository.findById(result);
        if(existOrNot.isPresent()){
            idGenerator();
        }
        return result;
    }
}
