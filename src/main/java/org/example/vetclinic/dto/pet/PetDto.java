package org.example.vetclinic.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Gender;
import org.example.vetclinic.entity.PetType;
import org.example.vetclinic.entity.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDto {

    private String name;
    private PetType petType;
    private Size size;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private double weight;
    private Gender gender;
    private int userId;
}
