package com.example.azurestoragedemo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private LocalDate dob;
    private Boolean hasbankAccount;
    private Boolean hasPolicy;

    @ContentId
    private String contentId;
    @ContentLength
    private long contentLength;
    private String contentMimeType = "text/plain";
}