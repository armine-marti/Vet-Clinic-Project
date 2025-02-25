package org.example.vetclinic.dto.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vetclinic.entity.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAppointmentRequest {

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;
    @NotNull(message = "Appointment time  is required")
    @FutureOrPresent(message = "Please choose an appropriate time for your appointment. Check our schedule if needed.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startTime;
    private Date finishTime;
    private Status status;

    private Integer petId;
    private String petName;

    private Integer doctorId;
    private String doctorSurname;

    private Integer userId;

    public void setFinishTime() {
        if (this.startTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.startTime);
            calendar.add(Calendar.MINUTE, 30);
            this.finishTime = calendar.getTime();
        }
    }

}
