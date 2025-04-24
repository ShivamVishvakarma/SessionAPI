package com.shivam.session.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SessionRequest {

    private String Name;

    private String Age;

    private String typeofsession;

    private String mentor;

    private LocalDateTime sessionTime;


}
