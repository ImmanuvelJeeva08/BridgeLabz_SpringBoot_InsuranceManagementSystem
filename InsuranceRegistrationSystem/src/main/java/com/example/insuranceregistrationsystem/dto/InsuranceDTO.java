package com.example.insuranceregistrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDTO {
    private int insuranceId;

    @Pattern(regexp = "^[A-Z][a-z\\sA-z]{2,}$", message = "insuranceCompanyName Name Invalid")
    private String insuranceCompanyName;

    @Pattern(regexp = "^[a-zA-z,/\\s0-9]{1,}$", message = "companyAddress Address Invalid")
    private String companyAddress;

    @Pattern(regexp = "^[0-9]{10}$", message = "User Phone Number Invalid")
    private String companyMobile;

}