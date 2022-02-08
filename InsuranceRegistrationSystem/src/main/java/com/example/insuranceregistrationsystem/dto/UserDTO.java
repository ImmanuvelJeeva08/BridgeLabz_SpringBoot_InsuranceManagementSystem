package com.example.insuranceregistrationsystem.dto;

import com.example.insuranceregistrationsystem.entity.Insurance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userId;

    @Pattern(regexp = "^[A-Z][a-z\\sA-z]{2,}$", message = "User Name Invalid")
    private String userFullName;

    @NotNull(message = "dob should not be Empty")
    private LocalDate dob;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Pattern(regexp = "^[a-zA-z/,\\s0-9]{1,}$", message = "User Address Invalid")
    private String address;

    @Pattern(regexp = "^[0-9]{10}$", message = "User Phone Number Invalid")
    private String mobileNo;

    @NotNull(message = "insuranceAmountPerMonth should not be Empty")
    private int insuranceAmountPerMonth;

    @NotNull(message = "insuranceYear should not be Empty")
    private int insuranceYear;

    @NotBlank(message = "vehicleModel cannot be empty")
    private String vehicleModel;

    @NotBlank(message = "vehicleNo cannot be empty")
    private String vehicleNo;

    private Date registerDate;
    private Date dataModifiedDate;
    private Insurance insurance;
}
