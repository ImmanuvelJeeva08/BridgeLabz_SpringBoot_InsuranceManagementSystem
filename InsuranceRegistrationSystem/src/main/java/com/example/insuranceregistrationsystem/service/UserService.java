package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.entity.DAOUser;
import com.example.insuranceregistrationsystem.entity.User;
import com.example.insuranceregistrationsystem.exception.InsuranceException;
import com.example.insuranceregistrationsystem.repository.InsuranceRepository;
import com.example.insuranceregistrationsystem.repository.UserDao;
import com.example.insuranceregistrationsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
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
    private InsuranceRepository insuranceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    public static int otpNumber = 0;
    public static DAOUser daoUser= null;
    public static User editUser = null;

    /****************************************************************************************************************************
     * Ability to add a new User details in database.
     * @param userDTO
     * @return userDTO
     ***************************************************************************************************************************/

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        userDTO.setRegisterDate(new Date());
        userDTO.setInsurance(insuranceRepository.findByInsuranceId(932));
        User addUser = modelMapper.map(userDTO, User.class);
        userRepository.save(addUser);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("immanuveljeeva2000@gmail.com");
            message.setTo(userDTO.getEmail());
            message.setSubject("Sucessfully Registration....");
            message.setText("New vehicle Insurance activated for given userDetails\n"  +
                    "contact : 9728172817\n" + "email   : immankrypc08@gmail.com\n" + "Address : 7,GandhiStreet, Chennai\n");
            message.setSentDate(new Date());
            javaMailSender.send(message);
        }catch (Exception e){
            throw new InsuranceException("Mail was not sent");
        }
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
        User user = userRepository.findUserByUserId(id);
        if(user != null){
            return user;
        }else {
            throw  new InsuranceException("Unable to find Requested User detail!");
        }
    }

    /**************************************************************************************************************************
     * Ability to delete the User records in database by userId.
     * @param userId
     **************************************************************************************************************************/

    @Override
    public void deleteUserById(int userId) {
        if(userId > 0) {
            System.out.println("ID = "+userId);
//            User deleteInsurance = getUserById(userId);
//            System.out.println(deleteInsurance);
//            userRepository.delete(deleteInsurance);
            userRepository.deleteByUserId(userId);
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
            System.out.println("Edit Details = "+editDetails);
            editDetails.setDataModifiedDate(new Date());
            String[] ignoreFields = { "userId","insuranceAmountPerMonth","insuranceYear","vehicleModel","vehicleNo", "vehicleType", "registerDate", "insurance" };
            BeanUtils.copyProperties(userDTO,editDetails,ignoreFields);
            editUser = editDetails;
            otpNumber = generateRandomOTP();
            editUser.setDataModifiedDate(new Date());
            String subject = "Email Verification ....";
            String text    = "Verification code : " + otpNumber;
            System.out.println("Edit User = "+editUser);
            emailService.sendEmail(editDetails.getEmail(), subject, text);
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
     * Ability to check userEmailId is present in database.
     * If exist,send $ digit otp to correspondent emailId.
     * @param email
     *************************************************************************************************************************/

    @Override
    public void resetPassword(String email) {
        daoUser = userDao.findByEmail(email);
        if(daoUser != null) {
            otpNumber = generateRandomOTP();
            String subject = "Email Verification ....";
            String text    = "Verification code : " + otpNumber;
            emailService.sendEmail(email, subject, text);
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

    @Override
    public User getUserDetailsByEmail(UserDetails userDetails) {
        System.out.println("User ="+userDetails.getPassword());
        DAOUser daoUser = userDao.findDAOUserByUsernameAndPassword(userDetails.getUsername(), userDetails.getPassword());
        System.out.println("DAO "+daoUser.getEmail());
        User user = userRepository.findUserByEmail(daoUser.getEmail());
        return user;
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

    public User editUserOTP(int otp){
        if(otp == otpNumber){
            System.out.println("edit user ID = "+ editUser.getUserId() );
            deleteUserById(editUser.getUserId());
            userRepository.save(editUser);
            return editUser;
        }else {
            throw new InsuranceException("OTP Invalid! Please Enter Correct Number");
        }
    }
}
