package com.example.insuranceregistrationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private String to;
    private String from;
    private String subject;
    private String body;
}
