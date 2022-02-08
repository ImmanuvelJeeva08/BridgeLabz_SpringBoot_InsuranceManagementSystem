package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.entity.DAOUser;
import com.example.insuranceregistrationsystem.entity.User;
import com.example.insuranceregistrationsystem.exception.InsuranceException;
import com.example.insuranceregistrationsystem.repository.UserDao;
import com.example.insuranceregistrationsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public static int otpNumber = 0;
    public static User addUser = null;
    public static DAOUser daoUser= null;

    /****************************************************************************************************************************
     * Ability to add a new User details in database.
     * @param userDTO
     * @return userDTO
     ***************************************************************************************************************************/

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        userDTO.setRegisterDate(new Date());
        addUser = modelMapper.map(userDTO, User.class);
        otpNumber = generateRandomOTP();
        emailService.sendEmail(userDTO.getEmail(),otpNumber);
//        emailService.sendEmailwithAttachment(userDTO.getEmail());
        return userDTO;
    }

    /****************************************************************************************************************************
     * Ability to retrive all userDetails from database.
     * @return allUsers
     ***************************************************************************************************************************/

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /****************************************************************************************************************************
     * Ability to retrive userDetails by userId from database.
     * If not found, throw an Exception.
     ***************************************************************************************************************************/

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InsuranceException("Unable to find Requested User detail!"));
    }

    /**************************************************************************************************************************
     * Ability to delete the User records in database by userId.
     * @param id
     **************************************************************************************************************************/

    @Override
    public void deleteUserById(int id) {
        if(id > 0) {
            User deleteInsurance = getUserById(id);
            userRepository.delete(deleteInsurance);
        }
    }

    /**************************************************************************************************************************
     * Ability to retrive userDetails details from database by userId.
     * Ability to edit the existing records in database and then add it to database.
     * @param id
     * @param userDTO
     * @return editDetails
     **************************************************************************************************************************/

    @Override
    public User editUser(int id, UserDTO userDTO) {
        User editDetails = null;
        if (id > 0){
            editDetails = getUserById(id);
            String[] ignoreFields = { "id" };
            BeanUtils.copyProperties(userDTO,editDetails,ignoreFields);
            userRepository.save(editDetails);
        }
        return editDetails;
    }

    /******************************************************************************************************************************
     * Generating random id for when adding new user details
     * @return result
     *****************************************************************************************************************************/

    @Override
    public int idGenerator() {
        Random r = new Random();
        int low = 100;
        int high = 999;
        int result = r.nextInt(high-low) + low;
        System.out.println("ID = "+result);
        Optional<User> existOrNot = userRepository.findById(result);
        if(existOrNot.isPresent()){
            idGenerator();
        }
        return result;
    }

    /**************************************************************************************************************************
     * Verifing the otp number.
     * @param otp
     **************************************************************************************************************************/

    @Override
    public void verifyOtpNumber(int otp) {
        if(otp == otpNumber){
            userRepository.save(addUser);
        }else {
            throw new InsuranceException("OTP INVALID! Please Enter correct OTP number");
        }
    }

    /**************************************************************************************************************************
     * Ability to check userEmailId is present in database.
     * If exist,send $ digit otp to correspondent emailId.
     * @param email
     *************************************************************************************************************************/

    @Override
    public void resetPassword(String email) {
        daoUser = userDao.findByEmail(email);
        if(daoUser != null) {
            otpNumber = generateRandomOTP();
            emailService.sendEmail(email, otpNumber);
        }else {
            throw new InsuranceException("Email Invalid! Please correct valid emailId");
        }
    }

    /***************************************************************************************************************************
     * Ability to verify the OTP number.
     * If verified , update the new User password to database.
     * @param otp
     * @param password
     **************************************************************************************************************************/

    @Override
    public void passwordChange(int otp, String password) {
        if(otpNumber == otp){
            System.out.println("Updated Password = "+password);
            daoUser.setPassword(bcryptEncoder.encode(password));
            userDao.save(daoUser);
        }else {
            throw new InsuranceException("OTP invalid! Please Enter Correct OTP Number");
        }
    }


    /*************************************************************************************************************************
     * Generate 4 digit randomNumber and send as otp to given emailId
     * @return
     *************************************************************************************************************************/

    public int generateRandomOTP(){
        int min = 1000;
        int max = 9999;
        Random rn = new Random();
        int result = rn.nextInt(max - min + 1) + min;
        System.out.println("OTP = "+result);
        return result;
    }
}
