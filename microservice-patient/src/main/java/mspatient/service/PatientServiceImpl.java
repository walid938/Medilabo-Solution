package mspatient.service;

import mspatient.model.Patient;
import mspatient.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> findById(int id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<Patient> findByFirstNameAndLastName(String firstName, String lastName) {
        return patientRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deleteById(int id) {
        patientRepository.deleteById(id);
    }

    @Override
    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        return !patientRepository.findByFirstNameAndLastName(firstName, lastName).isEmpty();
    }


}
