package com.student.registration.student_registration_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String course;

    @Column(name = "highest_education")
    private String studentClass;

    private Integer percentage;

    @Column(nullable = false)
    private String branch;

    @Column(name = "mobile")
    private String mobileNumber;
}

