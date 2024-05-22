package mspatient.service;

import mspatient.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PatientService {
    List<Patient> findAll();
    Optional<Patient> findById(Long id);
    List<Patient> findByFirstNameAndLastName(String firstName, String lastName);
    Patient save(Patient patient);
    void deleteById(Long id);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
