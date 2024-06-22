package msclientui.controller;

import msclientui.beans.DiabeteRiskLevelBeans;
import msclientui.beans.NoteBeans;
import msclientui.beans.PatientBeans;
import msclientui.proxies.DiabeteRiskProxy;
import msclientui.proxies.NoteProxy;
import msclientui.proxies.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private PatientProxy patientProxy;

    @Mock
    private NoteProxy noteProxy;

    @Mock
    private DiabeteRiskProxy diabeteRiskProxy;

    @InjectMocks
    private ClientController clientController;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListPatients() {
        // Given
        List<PatientBeans> patients = Arrays.asList(new PatientBeans(), new PatientBeans());
        when(patientProxy.getAllPatients()).thenReturn(patients);

        // When
        String viewName = clientController.listPatients(model);

        // Then
        verify(model).addAttribute(eq("patients"), eq(patients));
        assertEquals("patient/list", viewName);
    }

    @Test
    void testShowUpdateForm() {
        // Given
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        // When
        String viewName = clientController.showUpdateForm(patientId, model);

        // Then
        verify(model).addAttribute(eq("patient"), eq(patient));
        assertEquals("patient/update", viewName);
    }

    @Test
    void testUpdatePatient_Success() {
        // Given
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        String viewName = clientController.updatePatient(patientId, patient, bindingResult, model);

        // Then
        verify(patientProxy).updatePatient(eq(patientId), eq(patient));
        verify(model).addAttribute(eq("patients"), anyList());
        assertEquals("redirect:/patient/list", viewName);
    }

    @Test
    void testUpdatePatient_ValidationFailed() {
        // Given
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = clientController.updatePatient(patientId, patient, bindingResult, model);

        // Then
        verify(model).addAttribute(eq("patient"), eq(patient));
        assertEquals("patient/update", viewName);
    }

    @Test
    void testShowAddForm() {
        // When
        String viewName = clientController.showAddForm(model);

        // Then
        verify(model).addAttribute(eq("patient"), any(PatientBeans.class));
        assertEquals("patient/add", viewName);
    }

    @Test
    void testAddPatient_Success() {
        // Given
        PatientBeans patient = new PatientBeans();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(patientProxy.searchPatients(anyString(), anyString())).thenReturn(Collections.emptyList());
        when(patientProxy.createPatient(patient)).thenReturn(patient);

        // When
        String viewName = clientController.addPatient(patient, bindingResult, model);

        // Then
        verify(patientProxy).createPatient(eq(patient));
        verify(model).addAttribute(eq("patients"), anyList());
        assertEquals("redirect:/patient/list", viewName);
    }

    @Test
    void testAddPatient_DuplicateName() {
        // Given
        PatientBeans patient = new PatientBeans();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(patientProxy.searchPatients(anyString(), anyString())).thenReturn(Arrays.asList(patient));

        // When
        String viewName = clientController.addPatient(patient, bindingResult, model);

        // Then
        verify(model).addAttribute(eq("errorMessage"), eq("Patient with the same name already exists."));
        assertEquals("patient/add", viewName);
    }

    @Test
    void testAddPatient_WithBindingErrors() {
        // Given
        PatientBeans patient = new PatientBeans();
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String viewName = clientController.addPatient(patient, bindingResult, model);

        // Then
        verify(bindingResult).hasErrors();
        verifyNoInteractions(patientProxy);
        verifyNoInteractions(noteProxy);
        verifyNoInteractions(diabeteRiskProxy);
        assertEquals("patient/add", viewName);
    }


    @Test
    void testDeletePatient() {
        // Given
        int patientId = 1;

        // When
        String viewName = clientController.deletePatient(patientId, model);

        // Then
        verify(patientProxy).deletePatient(eq(patientId));
        verify(noteProxy).deleteNoteByPatientId(eq(patientId));
        assertEquals("redirect:/patient/list", viewName);
    }

    @Test
    void testSearchPatientForm() {
        // When
        String viewName = clientController.searchPatientForm(model);

        // Then
        assertEquals("patient/search", viewName);
    }

    @Test
    void testSearchPatients() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        List<PatientBeans> patients = Arrays.asList(new PatientBeans(), new PatientBeans());
        when(patientProxy.searchPatients(eq(firstName), eq(lastName))).thenReturn(patients);

        // When
        String viewName = clientController.searchPatients(firstName, lastName, model);

        // Then
        verify(patientProxy).searchPatients(eq(firstName), eq(lastName));
        verify(model).addAttribute(eq("patients"), eq(patients));
        assertEquals("patient/list", viewName);
    }

    @Test
    void testShowPatientDetails_withNoteId() {
        // Given
        int patientId = 1;
        String noteId = "noteId";
        PatientBeans patient = new PatientBeans();
        List<NoteBeans> notes = Arrays.asList(new NoteBeans(), new NoteBeans());
        NoteBeans note = new NoteBeans();
        DiabeteRiskLevelBeans riskLevel = DiabeteRiskLevelBeans.NONE;

        when(patientProxy.getPatientById(patientId)).thenReturn(patient);
        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        when(diabeteRiskProxy.calculateDiabetesRisk(patientId)).thenReturn(riskLevel);
        when(noteProxy.getNoteById(noteId)).thenReturn(note);

        // When
        String viewName = clientController.showPatientDetails(model, patientId, noteId);

        // Then
        verify(patientProxy).getPatientById(eq(patientId));
        verify(noteProxy).getNotesByPatientId(eq(patientId));
        verify(diabeteRiskProxy).calculateDiabetesRisk(eq(patientId));
        verify(noteProxy).getNoteById(eq(noteId));
        verify(model).addAttribute(eq("patient"), eq(patient));
        verify(model).addAttribute(eq("notes"), eq(notes));
        verify(model).addAttribute(eq("diabetesRiskLevel"), eq(riskLevel));
        verify(model).addAttribute(eq("noteToSave"), eq(note));
        assertEquals("patient/patient-details", viewName);
    }



    @Test
    void testSaveNote_Add() {
        // Given
        NoteBeans note = new NoteBeans();
        note.setPatientId(1);

        // When
        String viewName = clientController.saveNote(note);

        // Then
        assertNull(note.getId()); // Ensure note ID is null or empty
        verify(noteProxy).addNoteToPatient(eq(note.getPatientId()), any(NoteBeans.class));
        assertEquals("redirect:/patient/details?patientId=" + note.getPatientId(), viewName);
    }

    @Test
    void testSaveNote_Update() {
        // Given
        NoteBeans note = new NoteBeans();
        note.setId("noteId");
        note.setPatientId(1);

        // When
        String viewName = clientController.saveNote(note);

        // Then
        verify(noteProxy).updateNoteById(eq(note.getId()), eq(note));
        assertEquals("redirect:/patient/details?patientId=" + note.getPatientId(), viewName);
    }

    @Test
    void testDeleteNoteById() {
        // Given
        String noteId = "noteId";
        int patientId = 1;

        // When
        String viewName = clientController.deleteNoteById(noteId, patientId);

        // Then
        verify(noteProxy).deleteNoteById(eq(noteId));
        assertEquals("redirect:/patient/details?patientId=" + patientId, viewName);
    }
}
