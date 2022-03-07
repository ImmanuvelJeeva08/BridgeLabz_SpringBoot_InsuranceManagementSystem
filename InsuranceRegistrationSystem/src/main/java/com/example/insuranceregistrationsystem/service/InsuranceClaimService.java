package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.ClaimDTO;
import com.example.insuranceregistrationsystem.entity.ClaimEntity;
import com.example.insuranceregistrationsystem.entity.User;
import com.example.insuranceregistrationsystem.exception.InsuranceException;
import com.example.insuranceregistrationsystem.repository.ClaimRepository;
import com.example.insuranceregistrationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
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
     * @param claimDTO
     * @return claimEntity
     ****************************************************************************************************************************/

    @Override
    public ClaimEntity claimInsurance(ClaimDTO claimDTO) {
        ClaimEntity claimEntity = new ClaimEntity();
        Optional<User> user = userRepository.findUserByUserFullNameAndMobileNoAndVehicleNo(claimDTO.getUser().getUserFullName(),
                claimDTO.getUser().getMobileNo(),claimDTO.getUser().getVehicleNo());
        if(user.isPresent()){
            claimEntity.setId(idGenerator());
            claimEntity.setClaimDate(new Date());
            claimEntity.setUser(user.get());
            int amount = user.get().getInsuranceAmountPerMonth() * user.get().getInsuranceYear();
            claimEntity.setTotalAmount(amount);
            imageProcess(claimDTO);
            claimEntity.setFilepath(claimDTO.getFilepath());
            claimEntity.setBill(claimDTO.getBill());
            claimEntity.setCheque(claimDTO.getCheque());
            claimEntity.setBase64image(null);
            claimEntity.setReason(claimDTO.getReason());
            claimEntity.setStatus("ON PROCESS");
            System.out.println(claimDTO);
            claimRepository.save(claimEntity);
//            String subject = "Your claimInsurance Application is In process!!";
//            String text = " You will get response In 24hrs - 48hrs\n" + " vehicleInsurance claim is in Process\n"  +
//                    "contact : 9728172817\n" + "email   : immankrypc08@gmail.com\n" + "Address : 7,GandhiStreet, Chennai\n";
//            emailService.sendEmail(claimEntity.getUser().getEmail(),subject,text);
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

    private void imageProcess(ClaimDTO claimDTO) {
        String extensions = "";

        String[] strings = claimDTO.getBase64image().split(",");

            switch (strings[0]) {
                case "data:image/jpeg;base64":
                    extensions = ".jpeg";
                    break;
                case "data:image/png;base64":
                    extensions = ".png";
                    break;
                case "data:image/jpg;base64":
                    extensions = ".jpg";
                    break;
                default:
                    throw new InsuranceException("Please upload correct image Format ! jpeg,jpg,png only Allowed");
            }

            byte[] byteImage = DatatypeConverter.parseBase64Binary(strings[1]);

            File imageFileFolder = new File("E:\\InsuranceUserImage");

            if (!imageFileFolder.exists()) {
                if (imageFileFolder.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Failed to create Directory");
                }
            }

            if (!imageFileFolder.isDirectory()) {
                throw new InsuranceException("File path Not Found ==>" + imageFileFolder.getPath());
            }

            int randomNo = randomNumberGenerator();

            File newImage = new File(imageFileFolder + "\\image" + randomNo + extensions);
            String path = newImage.getPath();

            try {
                if (newImage.createNewFile()) {
                    System.out.println("Sucessfully Creadted Path ===> " + path);
                } else {
                    throw new InsuranceException("File already Exit");
                }
            } catch (IOException e) {
                throw new InsuranceException(e.getMessage());
            }

            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newImage))) {
                outputStream.write(byteImage);
                outputStream.close();
                claimDTO.setFilepath(path);
            } catch (Exception e) {
                throw new InsuranceException("Error occured in writing byteData in the File");
            }

    }

    private int randomNumberGenerator() {
        int min = 1;
        int max = 10000000;

        Random random = new Random();
        int result = random.nextInt(max - min) + min;
        return result;
    }
}
