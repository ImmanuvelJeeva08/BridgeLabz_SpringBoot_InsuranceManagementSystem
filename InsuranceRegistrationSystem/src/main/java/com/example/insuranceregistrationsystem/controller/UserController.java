package com.example.insuranceregistrationsystem.controller;

import com.example.insuranceregistrationsystem.dto.ResponseDTO;
import com.example.insuranceregistrationsystem.dto.UserDTO;
import com.example.insuranceregistrationsystem.dto.UserLoginDTO;
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
        ResponseDTO responseDTO = new ResponseDTO(addUser, "Sucessfully send account activation Link to your EmailId");
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
        System.out.println(userDTO);
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

    @PostMapping("/otp")
    public ResponseEntity<ResponseDTO> updateUser (@RequestBody UserLoginDTO userLoginDTO) {
        User updatedUser =userService.editUserOTP(userLoginDTO.getOtp());
        ResponseDTO responseDTO = new ResponseDTO(updatedUser, "OTP verified! Sucessfully updated user details");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
