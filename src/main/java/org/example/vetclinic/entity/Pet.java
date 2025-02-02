package org.example.vetclinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "pet")

public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private PetType petType;
    @Enumerated(EnumType.STRING)
    private Size size;
    private Date birthday;
    private double weight;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

}
