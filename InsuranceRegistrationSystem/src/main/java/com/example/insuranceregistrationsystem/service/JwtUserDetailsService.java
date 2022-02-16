package com.example.insuranceregistrationsystem.service;

import com.example.insuranceregistrationsystem.exception.InsuranceException;
import com.example.insuranceregistrationsystem.repository.UserDao;
import com.example.insuranceregistrationsystem.dto.UserLoginDTO;
import com.example.insuranceregistrationsystem.entity.DAOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService, Authentication {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    public static int otpNumber = 0000;
    public static DAOUser newUser = new DAOUser();


    /*****************************************************************************************************8*********************
     * Ability to retrive the User credential from databse.
     * @param username
     * @return user
     * @throws UsernameNotFoundException
     **************************************************************************************************************************/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    /*************************************************************************************************************************
     * Ability to add new User credential to database if OTP verified.
     * @param otp
     ************************************************************************************************************************/

    public DAOUser save(int otp) {
        if(otp == otpNumber){
            userDao.save(newUser);
            return newUser;
        }else {
            throw new InsuranceException("OTP invalid! Please Enter valid OTP NUMBER");
        }
    }

    //Ability to authenticate username and password.
    @Override
    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new InsuranceException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new InsuranceException("INVALID_CREDENTIALS");
        }
    }

    @Override
    public DAOUser registerUser(UserLoginDTO user) {
        newUser.setId(insuranceService.idGenerator());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        otpNumber = userService.generateRandomOTP();
        String subject = "Email Verification ....";
        String text    = "Verification code : " + otpNumber;
        emailService.sendEmail(newUser.getEmail(),subject,text);
        return newUser;
    }
}
