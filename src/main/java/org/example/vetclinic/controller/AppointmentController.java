package org.example.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.dto.appointment.AppointmentDto;
import org.example.vetclinic.dto.appointment.SaveAppointmentRequest;
import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.dto.pet.PetDtoBooking;
import org.example.vetclinic.entity.*;
import org.example.vetclinic.mapper.AppointmentMapper;
import org.example.vetclinic.mapper.PetMapper;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.service.AppointmentService;
import org.example.vetclinic.service.DoctorService;
import org.example.vetclinic.service.PetService;
import org.example.vetclinic.service.UserService;
import org.example.vetclinic.util.StartTimeCheck;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final PetMapper petMapper;
    private final UserService userService;

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

    @GetMapping("/allAppointments")
    public String allAppointments(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {

        List<AppointmentDto> appointments = appointmentMapper.toDtoList(
                appointmentService.getAllAppointments()
        );

        modelMap.put("appointments", appointments);
        modelMap.put("currentUser", currentUser);

        return "appointment/allAppointments";
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
            List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
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
            List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
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
        saveAppointmentRequest.setStatus(Status.BOOKED);
        appointmentService.save(saveAppointmentRequest);

        return "redirect:/appointments";

    }

    @GetMapping("/editAppointment")
    public String editAppointment(@RequestParam("title") String title, @RequestParam("userSurname") String userSurname,
                                  ModelMap modelMap) {
        Appointment appointmentOrNull = appointmentService.getByTitleAndUserSurname(title, userSurname);
        if (appointmentOrNull != null) {
            modelMap.put("saveAppointmentRequest", appointmentMapper.toSaveAppointmentRequest(appointmentOrNull));
            List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT,
                    appointmentOrNull.getUser().getId());
            List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
            modelMap.put("pets", pets);
            modelMap.put("doctors", doctors);
            return "appointment/editAppointment";
        }
        return "appointment/allAppointments";
    }

    @PostMapping("/editAppointment")
    public String editAppointment(
            @Valid @ModelAttribute SaveAppointmentRequest saveAppointmentRequest,
            @RequestParam("userId") int userId,
            @RequestParam("oldTitle") String oldTitle,
            @RequestParam("oldStartTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date oldStartTime,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            ModelMap modelMap,
            @AuthenticationPrincipal CurrentUser currentUser) {

        if (bindingResult.hasErrors()) {
            List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT, userId);
            List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
            modelMap.put("pets", pets);
            modelMap.put("doctors", doctors);
            modelMap.put("currentUser", currentUser);
            modelMap.put("bindingResult", bindingResult);
            return "appointment/addAppointment";
        }

        Appointment appointment = appointmentService.getByTitleAndUserId(oldTitle, userId);

        if (appointment == null) {
            bindingResult.rejectValue("title", "error.saveAppointmentRequest",
                    "Appointment not found");
            return "appointment/editAppointment";
        }

        boolean isAppointmentTitleUnique = appointmentService.existsByTitleAndUserId(saveAppointmentRequest.getTitle(),
                userId);
        if (!oldTitle.equals(saveAppointmentRequest.getTitle()) && isAppointmentTitleUnique) {
            bindingResult.rejectValue("title", "error.saveAppointmentRequest",
                    "Please choose a different title for the appointment!");
            List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT,
                    saveAppointmentRequest.getUserId());
            List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
            modelMap.put("pets", pets);
            modelMap.put("doctors", doctors);
            return "appointment/editAppointment";
        }

        Date newStartTime = saveAppointmentRequest.getStartTime();
        int doctorId = saveAppointmentRequest.getDoctorId();


        if (!newStartTime.equals(oldStartTime)) {

            if (!startTimeCheck.validateAppointmentsTime(newStartTime, doctorId)) {
                redirectAttributes.addFlashAttribute("error",
                        "Sorry, the doctor is not available at this time! Please choose another time or doctor.");
                return "redirect:editAppointment";
            }


            if (appointmentService.existsByStartTimeAndUserId(newStartTime, userId)) {
                redirectAttributes.addFlashAttribute("error",
                        "User already has an appointment at this time! Please choose another time and date.");
                return "redirect:editAppointment";
            }
        }

        saveAppointmentRequest.setFinishTime();
        saveAppointmentRequest.setUserId(userId);
        Appointment updatedAppointment = appointmentMapper.partialUpdate(saveAppointmentRequest, appointment);
        updatedAppointment.setStatus(saveAppointmentRequest.getStatus());
        appointmentService.save(updatedAppointment);

        return "redirect:/appointments/allAppointments";
    }


    @PostMapping("/cancelAppointment")
    public String cancelAppointment(
            @RequestParam("title") String title,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        User user = currentUser.getUser();
        appointmentService.cancelAppointment(title, user.getId());
        redirectAttributes.addFlashAttribute("success", "Appointment has been canceled!");
        return "redirect:/appointments";

    }

    @PostMapping("/deleteAppointment")
    public String deleteAppointment(
            @RequestParam("title") String title,
            @RequestParam("userId") int userId,
            RedirectAttributes redirectAttributes
    ) {

        Appointment appointment = appointmentService.getByTitleAndUserId(title, userId);
        appointmentService.deleteAppointment(title, userId);
        redirectAttributes.addFlashAttribute("success", "Appointment has been deleted!");
        return "redirect:/appointments/allAppointments";

    }


    @GetMapping("/addAppointmentForAdmin")
    public String addAppointmentForAdmin(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
        List<User> users = userService.getAll();
        modelMap.put("saveAppointmentRequest", new SaveAppointmentRequest());
        modelMap.put("currentUser", currentUser);
        modelMap.put("users", users);
        modelMap.put("doctors", doctors);

        return "appointment/addAppointmentForAdmin";
    }

    @PostMapping("/addAppointmentForAdmin")
    public String addAppointmentForAdmin(
            @Valid @ModelAttribute SaveAppointmentRequest saveAppointmentRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, ModelMap modelMap,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        if (bindingResult.hasErrors()) {
            List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT,
                    saveAppointmentRequest.getUserId());
            List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
            List<User> users = userService.getAll();
            modelMap.put("pets", pets);
            modelMap.put("doctors", doctors);
            modelMap.put("users", users);
            modelMap.put("currentUser", currentUser);
            modelMap.put("bindingResult", bindingResult);
            return "appointment/addAppointmentForAdmin";
        }

        boolean isAppointmentTitleUnique = appointmentService.existsByTitleAndUserId(saveAppointmentRequest.
                getTitle(), saveAppointmentRequest.getUserId());
        if (isAppointmentTitleUnique) {
            bindingResult.rejectValue("title", "error.saveAppointmentRequest",
                    "Please choose different title for appointment!");
            List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT,
                    saveAppointmentRequest.getUserId());
            List<User> users = userService.getAll();
            List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
            modelMap.put("pets", pets);
            modelMap.put("users", users);
            modelMap.put("doctors", doctors);
            return "appointment/addAppointmentForAdmin";
        }

        Date startTime = saveAppointmentRequest.getStartTime();
        int doctorId = saveAppointmentRequest.getDoctorId();

        if (!startTimeCheck.validateAppointmentsTime(startTime, doctorId)) {
            redirectAttributes.addFlashAttribute("error",
                    "Sorry, the doctor is not available at this time! Please choose another time or doctor");
            return "redirect:addAppointmentForAdmin";
        }
        if (appointmentService.existsByStartTimeAndUserId(startTime, saveAppointmentRequest.getUserId())) {
            redirectAttributes.addFlashAttribute("error",
                    "You already have an appointment booked for this time! Please choose another time and date");
            return "redirect:addAppointmentForAdmin";
        }

        saveAppointmentRequest.setFinishTime();
        saveAppointmentRequest.setUserId(saveAppointmentRequest.getUserId());
        saveAppointmentRequest.setStatus(Status.BOOKED);
        appointmentService.save(saveAppointmentRequest);

        return "redirect:/appointments/allAppointments";

    }

    @GetMapping("/getPetsForUser/{userId}")
    public String getPetsForUser(@PathVariable int userId, Model model) {
        List<PetDtoBooking> pets = petService.getAllByStatusPetAndUserIdForBooking(StatusPet.PRESENT, userId);
        model.addAttribute("pets", pets);
        return "fragments :: petList";
    }
}
