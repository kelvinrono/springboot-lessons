package com.demoapp.students.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "passports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String passportNumber;

    @OneToOne(mappedBy = "passport")
    private Student student;
}
