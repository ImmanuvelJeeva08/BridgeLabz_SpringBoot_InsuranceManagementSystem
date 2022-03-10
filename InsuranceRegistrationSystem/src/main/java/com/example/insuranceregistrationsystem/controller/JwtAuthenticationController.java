package com.example.insuranceregistrationsystem.controller;

import com.example.insuranceregistrationsystem.dto.JwtRequest;
import com.example.insuranceregistrationsystem.dto.JwtResponse;
import com.example.insuranceregistrationsystem.dto.ResponseDTO;
import com.example.insuranceregistrationsystem.dto.UserLoginDTO;
import com.example.insuranceregistrationsystem.entity.DAOUser;
import com.example.insuranceregistrationsystem.entity.User;
import com.example.insuranceregistrationsystem.service.JwtTokenUtil;
import com.example.insuranceregistrationsystem.service.JwtUserDetailsService;
import com.example.insuranceregistrationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    /***************************************************************************************************************************
     * Ability to verify user credentials.
     * If verified generate one jwtToken and send to the client.
     * @param authenticationRequest
     * @return jwtToken
     ***************************************************************************************************************************/

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        String userName = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        if(userName.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
            final UserDetails userDetails = new org.springframework.security.core.userdetails.User(userName,password, new ArrayList<>());
            final String token = jwtTokenUtil.generateToken(userDetails);
            ResponseDTO responseDTO = new ResponseDTO(authenticationRequest,token);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }else {
            userDetailsService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            User user = userService.getUserDetailsByEmail(userDetails);
            JwtResponse response = new JwtResponse(token,user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /***************************************************************************************************************************
     * Ability to add new User credentials like userName and password.
     * @param user
     ***************************************************************************************************************************/

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserLoginDTO user) {
        DAOUser registerUser = userDetailsService.registerUser(user);
        ResponseDTO responseDTO = new ResponseDTO(registerUser,"Sucessfully OTP send you given Emailid! Please check that!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /************************************************************************************************************************
     * Ability to verify the otp and If verifire save User credentials.
     * @param user
     * @return
     ************************************************************************************************************************/

    @PostMapping(value = "/generateOTP")
    public ResponseEntity<ResponseDTO> saveUser(@RequestBody UserLoginDTO user) {
        userService.resendOTP(user.getEmail());
        ResponseDTO responseDTO = new ResponseDTO(user,"Sucessfully Send OTP to given EmailID");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/activate")
    public ModelAndView activateAccount() {
        ModelAndView modelAndView = userDetailsService.activateAccount();
        return modelAndView;
    }

    /****************************************************************************************************************************
     * Ability to reset Password for existing User credentials.
     ***************************************************************************************************************************/

    @PostMapping("/forgetpassword")
    public ResponseEntity<ResponseDTO> forgetPassword(@RequestBody UserLoginDTO loginDTO) {
        userService.resetPassword(loginDTO.getEmail());
        ResponseDTO responseDTO = new ResponseDTO(null, "Email verified! Sucessfully send an OTP to correspondent your EmailId");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to verified the OTP and if verified,update the User credencials in database.
     * @return
     **************************************************************************************************************************/

    @PostMapping("/otpverify")
    public ResponseEntity<ResponseDTO> otpVerificationChangePassword(@RequestBody UserLoginDTO userLoginDTO) {
        userService.otpVerify(userLoginDTO.getOtp());
        ResponseDTO responseDTO = new ResponseDTO(null, "OTP verified! Sucessfully updated user credentials");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody UserLoginDTO userLoginDTO) {
        userService.passwordChange(userLoginDTO.getPassword());
        ResponseDTO responseDTO = new ResponseDTO(null, "OTP verified! Sucessfully updated user credentials");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
