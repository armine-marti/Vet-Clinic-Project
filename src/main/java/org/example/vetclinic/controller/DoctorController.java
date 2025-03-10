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

/**
 * Controller responsible for handling actions related to doctors including viewing,
 * adding, editing, and deleting doctor records.
 */
@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    /**
     * Displays all doctors who are currently active employees.
     *
     * @param modelMap the model used to pass the list of doctors to the view.
     * @return the view name for displaying the list of doctors.
     */
    @GetMapping
    public String allDoctors(ModelMap modelMap) {
        List<DoctorDto> doctors = doctorService.getAllByStatusDoctor(StatusDoctor.CURRENT_EMPLOYEE);
        modelMap.put("doctors", doctors);
        return "doctor/doctors";
    }

    /**
     * Displays the form for adding a new doctor.
     *
     * @param modelMap the model used to add an empty SaveDoctorRequest object to the view.
     * @return the view name for the add doctor form.
     */
    @GetMapping("/addDoctor")
    public String addDoctor(ModelMap modelMap) {

        modelMap.put("saveDoctorRequest", new SaveDoctorRequest());
        return "doctor/addDoctor";
    }

    /**
     * Handles the form submission for adding a new doctor. If there are validation errors or if
     * a doctor with the same email already exists, the user is redirected back to the add doctor form.
     *
     * @param saveDoctorRequest  the details for the new doctor to be added.
     * @param bindingResult      contains validation errors, if any.
     * @param redirectAttributes used to add flash attributes (success or error messages).
     * @param modelMap           used to pass the validation result back to the view.
     * @return the view name for redirection after successful addition or to show errors.
     */
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

    /**
     * Displays the form for editing an existing doctor.
     *
     * @param email    the email of the doctor to be edited.
     * @param surname  the surname of the doctor to be edited.
     * @param modelMap the model used to pass the existing doctor's details to the view.
     * @return the view name for editing the doctor, or the list of doctors if the doctor is not found.
     */
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

    /**
     * Handles the form submission for editing a doctor. If there are validation errors or if the
     * doctor with the same email already exists, the user is redirected back to the edit doctor form.
     *
     * @param saveDoctorRequest the details for the doctor to be edited.
     * @param oldEmail          the current email of the doctor to be edited.
     * @param surname           the current surname of the doctor to be edited.
     * @param bindingResult     contains validation errors, if any.
     * @param modelMap          used to pass the validation result back to the view.
     * @return the view name for redirection after successful update or to show errors.
     */
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
            bindingResult.rejectValue("email", "error.saveDoctorRequest",
                    "Please choose a different email for the doctor!");
            if (doctor == null) {
                bindingResult.rejectValue("name", "error.saveDoctorRequest",
                        "Doctor not found");
            }
            return "doctor/editDoctor";
        }

        doctor.setSurname(surname);
        Doctor updatedDoctor = doctorMapper.partialUpdate(saveDoctorRequest, doctor);
        updatedDoctor.setStatusDoctor(saveDoctorRequest.getStatusDoctor());
        doctorService.save(updatedDoctor);

        return "redirect:/doctors";

    }

    /**
     * Handles the deletion of a doctor based on their ID.
     *
     * @param doctorId           the ID of the doctor to be deleted.
     * @param redirectAttributes used to add flash attributes (success or error messages).
     * @return the view name for redirection after successful deletion.
     */
    @PostMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("id") int doctorId, RedirectAttributes redirectAttributes) {
        doctorService.deleteDoctor(doctorId);
        redirectAttributes.addFlashAttribute("success", "Doctor has been deleted!");
        return "redirect:/doctors";
    }
}
