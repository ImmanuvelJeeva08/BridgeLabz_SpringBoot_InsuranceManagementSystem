package com.example.insuranceregistrationsystem.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class DAOUser {

    @Id
    private int id;

    private String username;
    private String password;
    private String email;
}
