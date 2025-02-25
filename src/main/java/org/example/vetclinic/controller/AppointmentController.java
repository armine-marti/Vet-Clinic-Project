package org.example.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.Status;
import org.example.vetclinic.entity.StatusPet;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.mapper.AppointmentMapper;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.AppointmentService;
import org.example.vetclinic.service.DoctorService;
import org.example.vetclinic.service.PetService;
import org.example.vetclinic.util.StartTimeCheck;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final StartTimeCheck startTimeCheck;
    private final PetService petService;
    private final DoctorService doctorService;
    private final AppointmentMapper appointmentMapper;

    @GetMapping
    public String userAppointments(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();

        List<AppointmentDto> appointments = appointmentMapper.toDtoList(
                appointmentService.getAllByStatusAndUserIdAndStartTimeIsFuture(Status.BOOKED, user.getId())
        );

        modelMap.put("appointments", appointments);
        modelMap.put("currentUser", currentUser);

        return "appointment/appointments";
    }

    @GetMapping("/addAppointment")
    public String addAppointment(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT,
                currentUser.getUser().getId());
        List<DoctorDto> doctors = doctorService.getAll();
        modelMap.put("saveAppointmentRequest", new SaveAppointmentRequest());
        modelMap.put("currentUser", currentUser);
        modelMap.put("pets", pets);
        modelMap.put("doctors", doctors);

        return "appointment/addAppointment";
    }

    @PostMapping("/addAppointment")
    public String addAppointment(
            @Valid @ModelAttribute SaveAppointmentRequest saveAppointmentRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, ModelMap modelMap,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        User user = currentUser.getUser();

        if (bindingResult.hasErrors()) {
            List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT, user.getId());
            List<DoctorDto> doctors = doctorService.getAll();
            modelMap.put("pets", pets);
            modelMap.put("doctors", doctors);
            modelMap.put("currentUser", currentUser);
            modelMap.put("bindingResult", bindingResult);
            return "appointment/addAppointment";
        }

        boolean isAppointmentTitleUnique = appointmentService.existsByTitleAndUserId(saveAppointmentRequest.
                getTitle(), user.getId());
        if (isAppointmentTitleUnique) {
            bindingResult.rejectValue("title", "error.saveAppointmentRequest",
                    "Please choose different title for appointment!");
            List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT, user.getId());
            List<DoctorDto> doctors = doctorService.getAll();
            modelMap.put("pets", pets);
            modelMap.put("doctors", doctors);
            return "appointment/addAppointment";
        }

        Date startTime = saveAppointmentRequest.getStartTime();
        int doctorId = saveAppointmentRequest.getDoctorId();

        if (!startTimeCheck.validateAppointmentsTime(startTime, doctorId)) {
            redirectAttributes.addFlashAttribute("error",
                    "Sorry, the doctor is not available at this time! Please choose another time or doctor");
            return "redirect:addAppointment";
        }
        if (appointmentService.existsByStartTimeAndUserId(startTime, user.getId())) {
            redirectAttributes.addFlashAttribute("error",
                    "You already have an appointment booked for this time! Please choose another time and date");
            return "redirect:addAppointment";
        }

        saveAppointmentRequest.setFinishTime();
        saveAppointmentRequest.setUserId(user.getId());
        appointmentService.save(saveAppointmentRequest);

        return "redirect:/appointments";

    }

    @PostMapping("/cancelAppointment")
    public String cancelAppointment(
            @RequestParam("title") String title,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        User user = currentUser.getUser();
        appointmentService.cancelAppointment(title, user.getId());
        redirectAttributes.addFlashAttribute("success", "Appointment has been deleted!");
        return "redirect:/appointments";

    }
}
