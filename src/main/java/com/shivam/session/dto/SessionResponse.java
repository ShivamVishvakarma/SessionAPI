package com.shivam.session.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SessionResponse {


    private Long id;
    private String Name;

    private String typeofsession;

    private String mentor;

    private LocalDateTime sessionTime;

    private boolean isCanceled;
}
