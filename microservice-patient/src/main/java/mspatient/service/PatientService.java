package mspatient.service;

import mspatient.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PatientService {

    List<Patient> getAllPatient();

    Optional<Patient> getPatientById(Long id);

    Patient createPatient(Patient patient);

    Patient updatePatient(Patient updatedpatient, Long id);

    void  deletePatient(Long id);

    List<Patient> getPatientByFirstNameAndLastName(String FirstName, String LastName);
}
