package com.example.insuranceregistrationsystem.controller;

import com.example.insuranceregistrationsystem.dto.ResponseDTO;
import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.entity.User;
import com.example.insuranceregistrationsystem.exception.InsuranceException;
import com.example.insuranceregistrationsystem.service.InsuranceService;
import com.example.insuranceregistrationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/userSystem")
public class UserController {

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    UserService userService;

    /***************************************************************************************************************************
     * Ability to add New User details to database
     * @param userDTO
     * @return addUser
     ***************************************************************************************************************************/

    @PostMapping(value = "/user")
    public ResponseEntity<ResponseDTO> addUserDetails(@Valid @RequestBody UserDTO userDTO){
        userDTO.setUserId(userService.idGenerator());
        UserDTO addUser = userService.addUser(userDTO);
        ResponseDTO responseDTO = new ResponseDTO(addUser, "Adding in Process! Check your Email and enter the OTP number");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to get all User details from database
     * @return UserDTOList
     ***************************************************************************************************************************/

    @GetMapping(value = "/allUsers")
    public ResponseEntity<ResponseDTO> getAllUserDetails(){
        List<User> UserDTOList = userService.getAllUsers();
        ResponseDTO responseDTO = new ResponseDTO(UserDTOList,"Fetched "+UserDTOList.size()+" User Details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to edit existing user details from database
     * @param userDTO
     * @return editUser
     ***************************************************************************************************************************/

    @PutMapping(value = "/user")
    public ResponseEntity<ResponseDTO> editUserDetails(@Valid @RequestBody UserDTO userDTO){
        User editUser = userService.editUser(userDTO.getUserId(),userDTO);
        ResponseDTO responseDTO = new ResponseDTO(editUser,"Sucessfully Updated given Details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to delete user details from database using ID
     * @param id
     * @return null
     ***************************************************************************************************************************/

    @DeleteMapping(value = "/user")
    public ResponseEntity<ResponseDTO> deleteUserDetails(@RequestParam(name = "id") int id){
        userService.deleteUserById(id);
        ResponseDTO responseDTO = new ResponseDTO(null,"Sucessfully Deleted");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to get Specific User details from database using ID
     * @param id
     * @return user
     ***************************************************************************************************************************/

    @GetMapping(value = "/userById")
    public ResponseEntity<ResponseDTO> getUserDetailsById(@RequestParam(name = "id") int id) throws Exception {
        try {
            User user = userService.getUserById(id);
            ResponseDTO responseDTO = new ResponseDTO(user,"Sucessfully Fetched Given Id details");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (InsuranceException e){
            throw new InsuranceException(e.getMessage());
        }
    }

    /**************************************************************************************************************************
     * Ability to Verifing the otp was send user Email.
     * IF verified added User Details.
     * @param otp
     *************************************************************************************************************************/

    @PostMapping("/verifyOTP")
    public ResponseEntity<ResponseDTO> verifyOTP(@RequestParam(name = "otp") int otp) {
        userService.verifyOtpNumber(otp);
        ResponseDTO responseDTO = new ResponseDTO(null, "OTP verified! Sucessfully added User details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /****************************************************************************************************************************
     * Ability to reset Password for existing User credentials.
     * @param email
     ***************************************************************************************************************************/

    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam(name = "email") String email) {
        userService.resetPassword(email);
        ResponseDTO responseDTO = new ResponseDTO(null, "Email verified! Sucessfully send an OTP to correspondent your EmailId");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /***************************************************************************************************************************
     * Ability to verified the OTP and if verified,update the User credencials in database.
     * @param password
     * @param otp
     * @return
     **************************************************************************************************************************/

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDTO> changePassword(@RequestParam String password,@RequestParam int otp) {
        userService.passwordChange(otp, password);
        ResponseDTO responseDTO = new ResponseDTO(null, "OTP verified! Sucessfully updated user credentials");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
