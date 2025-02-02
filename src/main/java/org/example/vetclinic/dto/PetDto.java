package org.example.vetclinic.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Gender;
import org.example.vetclinic.entity.PetType;
import org.example.vetclinic.entity.Size;
import org.example.vetclinic.entity.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDto {

    private int id;
    private String name;
    private PetType type;
    private Size size;
    private Date birthday;
    private double weight;
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;


}
