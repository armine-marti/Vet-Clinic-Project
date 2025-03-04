package org.example.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vetclinic.dto.doctor.DoctorDto;
import org.example.vetclinic.dto.doctor.SaveDoctorRequest;
import org.example.vetclinic.entity.Doctor;
import org.example.vetclinic.entity.StatusDoctor;
import org.example.vetclinic.mapper.DoctorMapper;
import org.example.vetclinic.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;


    @GetMapping
    public String allDoctors(ModelMap modelMap) {
        List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
        modelMap.put("doctors", doctors);
        return "doctor/doctors";
    }


    @GetMapping("/addDoctor")
    public String addDoctor(ModelMap modelMap) {

        modelMap.put("saveDoctorRequest", new SaveDoctorRequest());
        return "doctor/addDoctor";
    }

    @PostMapping("/addDoctor")
    public String addDoctor(@Valid @ModelAttribute SaveDoctorRequest saveDoctorRequest,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.put("bindingResult", bindingResult);
            return "doctor/addDoctor";
        }

        boolean doctorExists = doctorService.existsByEmail(saveDoctorRequest.getEmail());
        if (doctorExists) {
            bindingResult.rejectValue("email", "error.savePetRequest",
                    "Doctor with same email already exists!");
            return "doctor/addDoctor";
        }
        Doctor doctor = doctorMapper.toEntity(saveDoctorRequest);
        doctorService.save(doctor);
        redirectAttributes.addFlashAttribute("success", "You have added a new doctor!");

        return "redirect:/doctors";
    }


    @GetMapping("/editDoctor")
    public String editDoctor(@RequestParam("email") String email, @RequestParam("surname") String surname,
                             ModelMap modelMap) {
        Doctor doctorOrNull = doctorService.getByEmailAndSurname(email, surname);
        if (doctorOrNull != null) {
            modelMap.put("saveDoctorRequest", doctorMapper.toSaveDoctorRequest(doctorOrNull));
            return "doctor/editDoctor";
        }
        return "doctor/doctors";
    }

    @PostMapping("/editDoctor")
    public String editDoctor(
            @Valid @ModelAttribute SaveDoctorRequest saveDoctorRequest,
            @RequestParam("oldEmail") String oldEmail,
            @RequestParam("surname") String surname,
            BindingResult bindingResult,
            ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            modelMap.put("bindingResult", bindingResult);
            return "doctor/editDoctor";
        }

        Doctor doctor = doctorService.getByEmail(oldEmail);

        if ((!oldEmail.equals(saveDoctorRequest.getEmail())
                && doctorService.existsByEmail(saveDoctorRequest.getEmail()))) {
            bindingResult.rejectValue("email", "error.saveDoctorRequest", "Please choose a different email for the doctor!");
            if (doctor == null) {
                bindingResult.rejectValue("name", "error.saveDoctorRequest", "Doctor not found");
            }
            return "doctor/editDoctor";
        }

        doctor.setSurname(surname);
        Doctor updatedDoctor = doctorMapper.partialUpdate(saveDoctorRequest, doctor);
        updatedDoctor.setStatusDoctor(saveDoctorRequest.getStatusDoctor());
        doctorService.save(updatedDoctor);

        return "redirect:/doctors";

    }

    @PostMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("id") int doctorId, RedirectAttributes redirectAttributes) {
        doctorService.deleteDoctor(doctorId);
        redirectAttributes.addFlashAttribute("success", "Doctor has been deleted!");
        return "redirect:/doctors";
    }
}
