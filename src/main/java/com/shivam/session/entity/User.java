package com.shivam.session.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String Email;

    private String Phone;



}
