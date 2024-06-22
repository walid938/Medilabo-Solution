package mspatient.controller;

import mspatient.model.Gender;
import mspatient.model.Patient;
import mspatient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPatients() {
        // Given
        List<Patient> patients = Arrays.asList(
                new Patient(1, "John", "Doe", LocalDate.of(1980, 10, 25), Gender.M, "Address 1", "1234567890"),
                new Patient(2, "Jane", "Smith", LocalDate.of(1985, 5, 15), Gender.F, "Address 2", "9876543210")
        );
        when(patientService.findAll()).thenReturn(patients);

        // When
        List<Patient> result = patientController.getAllPatients();

        // Then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals(Gender.M, result.get(0).getGender());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Smith", result.get(1).getLastName());
        assertEquals(Gender.F, result.get(1).getGender());
    }


    @Test
    public void testGetPatientById() {
        // Given
        int patientId = 1;
        Patient patient = new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), Gender.M, "Address 1", "1234567890");
        when(patientService.findById(patientId)).thenReturn(Optional.of(patient));

        // When
        ResponseEntity<Patient> response = patientController.getPatientById(patientId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientId, response.getBody().getId());
    }

    @Test
    public void testGetPatientByIdNotFound() {
        // Given
        int patientId = 999;
        when(patientService.findById(patientId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Patient> response = patientController.getPatientById(patientId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchPatients() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        List<Patient> patients = Arrays.asList(new Patient(1, firstName, lastName, LocalDate.of(1980, 1, 1), Gender.M, "Address 1", "1234567890"));
        when(patientService.findByFirstNameAndLastName(firstName, lastName)).thenReturn(patients);

        // When
        List<Patient> result = patientController.searchPatients(firstName, lastName);

        // Then
        assertEquals(1, result.size());
        assertEquals(firstName, result.get(0).getFirstName());
        assertEquals(lastName, result.get(0).getLastName());
    }

    @Test
    public void testCreatePatient() {
        // Given
        Patient patientToSave = new Patient(1, "Jane", "Doe", LocalDate.of(1985, 5, 15), Gender.F, "Address 2", "9876543210");
        Patient savedPatient = new Patient(1, "Jane", "Doe", LocalDate.of(1985, 5, 15), Gender.F, "Address 2", "9876543210");
        when(patientService.save(any(Patient.class))).thenReturn(savedPatient);

        // When
        Patient result = patientController.createPatient(patientToSave);

        // Then
        assertEquals(savedPatient.getId(), result.getId());
        assertEquals(savedPatient.getFirstName(), result.getFirstName());
        assertEquals(savedPatient.getLastName(), result.getLastName());
        assertEquals(savedPatient.getGender(), result.getGender());
        assertEquals(savedPatient.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(savedPatient.getAddress(), result.getAddress());
        assertEquals(savedPatient.getPhoneNumber(), result.getPhoneNumber());

        // Verify that save method was called with the expected patient
        verify(patientService, times(1)).save(patientToSave);
    }



    @Test
    public void testUpdatePatient() {
        // Given
        int patientId = 1;
        Patient patientToUpdate = new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), Gender.M, "Address 1", "1234567890");
        Patient updatedPatientDetails = new Patient(patientId, "Updated", "Patient", LocalDate.of(1990, 1, 1), Gender.F, "Updated Address", "9876543210");
        when(patientService.findById(patientId)).thenReturn(Optional.of(patientToUpdate));
        when(patientService.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ResponseEntity<Patient> response = patientController.updatePatient(patientId, updatedPatientDetails);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPatientDetails.getFirstName(), response.getBody().getFirstName());
        assertEquals(updatedPatientDetails.getLastName(), response.getBody().getLastName());
        assertEquals(updatedPatientDetails.getDateOfBirth(), response.getBody().getDateOfBirth());
        assertEquals(updatedPatientDetails.getGender(), response.getBody().getGender());
        assertEquals(updatedPatientDetails.getAddress(), response.getBody().getAddress());
        assertEquals(updatedPatientDetails.getPhoneNumber(), response.getBody().getPhoneNumber());
    }

    @Test
    public void testDeletePatient() {
        // Given
        int patientId = 1;
        when(patientService.findById(patientId)).thenReturn(Optional.of(new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), Gender.M, "Address 1", "1234567890")));

        // When
        ResponseEntity<Void> response = patientController.deletePatient(patientId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(patientService, times(1)).deleteById(patientId);
    }

    @Test
    public void testDeletePatientNotFound() {
        // Given
        int patientId = 999;
        when(patientService.findById(patientId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Void> response = patientController.deletePatient(patientId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patientService, never()).deleteById(anyInt());
    }
}
