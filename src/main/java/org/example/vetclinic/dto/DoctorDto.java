package org.example.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Specialization;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {

    private int id;
    private String name;
    private String surname;
    private Specialization specialization;
}
