package msclientui.controller;


import jakarta.validation.Valid;
import msclientui.beans.NoteBeans;
import msclientui.beans.PatientBeans;
import msclientui.proxies.DiabeteRiskProxy;
import msclientui.proxies.NoteProxy;
import msclientui.proxies.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private NoteProxy noteProxy;
    @Autowired
    private DiabeteRiskProxy diabeteRiskProxy;

    public ClientController(PatientProxy patientProxy,NoteProxy noteProxy, DiabeteRiskProxy diabeteRiskProxy) {
        this.patientProxy = patientProxy;
        this.noteProxy = noteProxy;
        this.diabeteRiskProxy = diabeteRiskProxy;
    }

    @GetMapping("/patient/list")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "patient/list";
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        PatientBeans patientBeans = patientProxy.getPatientById(id);
        model.addAttribute("patient", patientBeans);
        return "patient/update";
    }

    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") int id, @Valid PatientBeans patient,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patient", patient);
            return "patient/update";
        }
        patientProxy.updatePatient(id, patient);
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new PatientBeans());
        return "patient/add";
    }

    @PostMapping("/patient/add")
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

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
        patientProxy.deletePatient(id);
        noteProxy.deleteNoteByPatientId(id);
        model.addAttribute("patients", patientProxy.getAllPatients());
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/search")
    public String searchPatientForm(Model model) {
        return "patient/search";
    }

    @PostMapping("/patient/search")
    public String searchPatients(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName, Model model) {
        List<PatientBeans> patients = patientProxy.searchPatients(firstName, lastName);
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/patient/details")
    public String showPatientDetails(Model model, @RequestParam int patientId,
                                     @RequestParam(required = false) String noteId) {
        model.addAttribute("patient", patientProxy.getPatientById(patientId));
        model.addAttribute("notes", noteProxy.getNotesByPatientId(patientId));
        model.addAttribute("diabetesRiskLevel", diabeteRiskProxy.calculateDiabetesRisk(patientId));
        if (noteId != null) {
            model.addAttribute("noteToSave", noteProxy.getNoteById(noteId));
        } else {
            model.addAttribute("noteToSave", new NoteBeans());
        }
        return "patient/patient-details";
    }

    @PostMapping("/note/save")
    public String saveNote(@ModelAttribute("noteToSave") NoteBeans note) {
        if (note.getId().isEmpty()) {
            note.setId(null);
            noteProxy.addNoteToPatient(note.getPatientId(), note);
        } else {
            noteProxy.updateNoteById(note.getId(), note);
        }
        return "redirect:/patient/details?patientId=" + note.getPatientId();
    }

    @GetMapping("/note/delete/{id}")
    public String deleteNoteById(@PathVariable("id") String id, @RequestParam int patientId) {
        noteProxy.deleteNoteById(id);
        return "redirect:/patient/details?patientId=" + patientId;
    }

}


