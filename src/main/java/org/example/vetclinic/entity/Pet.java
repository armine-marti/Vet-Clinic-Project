package org.example.vetclinic.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Entity representing a pet.
 * This class maps to the `pet` table in the database and stores details about a pet,
 * including its name, type, size, birthday, weight, gender, owner, and status.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "pet", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "owner_id"})
})

public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private PetType petType;
    @Enumerated(EnumType.STRING)
    private Size size;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private double weight;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    private StatusPet statusPet;

}
