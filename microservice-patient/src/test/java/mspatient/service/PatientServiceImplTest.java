package mspatient.service;

import mspatient.model.Patient;
import mspatient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        patient1 = new Patient();
        patient1.setId(1);
        patient1.setFirstName("John");
        patient1.setLastName("Doe");

        patient2 = new Patient();
        patient2.setId(2);
        patient2.setFirstName("Jane");
        patient2.setLastName("Smith");
    }

    @Test
    void testFindAll() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));

        List<Patient> patients = patientService.findAll();

        assertNotNull(patients);
        assertEquals(2, patients.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient1));

        Optional<Patient> patient = patientService.findById(1);

        assertTrue(patient.isPresent());
        assertEquals("John", patient.get().getFirstName());
        verify(patientRepository, times(1)).findById(1);
    }

    @Test
    void testFindByFirstNameAndLastName() {
        when(patientRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Arrays.asList(patient1));

        List<Patient> patients = patientService.findByFirstNameAndLastName("John", "Doe");

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getFirstName());
        verify(patientRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testSave() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient1);

        Patient savedPatient = patientService.save(patient1);

        assertNotNull(savedPatient);
        assertEquals("John", savedPatient.getFirstName());
        verify(patientRepository, times(1)).save(patient1);
    }

    @Test
    void testDeleteById() {
        doNothing().when(patientRepository).deleteById(1);

        patientService.deleteById(1);

        verify(patientRepository, times(1)).deleteById(1);
    }

    @Test
    void testExistsByFirstNameAndLastName() {
        when(patientRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Arrays.asList(patient1));

        boolean exists = patientService.existsByFirstNameAndLastName("John", "Doe");

        assertTrue(exists);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }
}

