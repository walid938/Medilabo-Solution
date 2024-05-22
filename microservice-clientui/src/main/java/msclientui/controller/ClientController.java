package msclientui.controller;


import jakarta.validation.Valid;
import msclientui.beans.PatientBeans;
import msclientui.proxies.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class ClientController {

    @Autowired
    private PatientProxy patientProxy;

    @GetMapping("/list")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "patient/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        PatientBeans patientBeans = patientProxy.getPatientById(id);
        model.addAttribute("patient", patientBeans);
        return "patient/update";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @Valid PatientBeans patient,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patient", patient);
            return "patient/update";
        }
        patientProxy.updatePatient(id, patient);
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "redirect:/patient/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new PatientBeans());
        return "patient/add";
    }

    @PostMapping("/add")
    public String addPatient(@Valid PatientBeans patientBeans, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "patient/add";
        }

        List<PatientBeans> existingPatients = patientProxy.searchPatients(patientBeans.getFirstName(), patientBeans.getLastName());
        if (!existingPatients.isEmpty()) {
            model.addAttribute("errorMessage", "Patient with the same name already exists.");
            return "patient/add";
        }

        patientProxy.createPatient(patientBeans);
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "redirect:/patient/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id, Model model) {
        patientProxy.deletePatient(id);
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "redirect:/patient/list";
    }

    @GetMapping("/search")
    public String searchPatientForm(Model model) {
        return "patient/search";
    }

    @PostMapping("/search")
    public String searchPatients(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName, Model model) {
        List<PatientBeans> patients = patientProxy.searchPatients(firstName, lastName);
        model.addAttribute("patients", patients);
        return "patient/list";
    }
}


