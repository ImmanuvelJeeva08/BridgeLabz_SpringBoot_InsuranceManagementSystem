package com.example.insuranceregistrationsystem.controller;

import com.example.insuranceregistrationsystem.dto.JwtRequest;
import com.example.insuranceregistrationsystem.dto.JwtResponse;
import com.example.insuranceregistrationsystem.dto.ResponseDTO;
import com.example.insuranceregistrationsystem.dto.UserLoginDTO;
import com.example.insuranceregistrationsystem.entity.DAOUser;
import com.example.insuranceregistrationsystem.service.JwtTokenUtil;
import com.example.insuranceregistrationsystem.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    /***************************************************************************************************************************
     * Ability to verify user credentials.
     * If verified generate one jwtToken and send to the client.
     * @param authenticationRequest
     * @return jwtToken
     ***************************************************************************************************************************/

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        userDetailsService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /***************************************************************************************************************************
     * Ability to add new User credentials like userName and password.
     * @param user
     ***************************************************************************************************************************/

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserLoginDTO user) {
        DAOUser registerUser = userDetailsService.registerUser(user);
        ResponseDTO responseDTO = new ResponseDTO(registerUser,"Sucessfully OTP send you given Emailid! Please check that!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestParam int otp) {
        DAOUser user = userDetailsService.save(otp);
        ResponseDTO responseDTO = new ResponseDTO(user,"Sucessfully User credentials added into the Database");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}