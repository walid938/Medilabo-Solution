package mspatient.repository;

import mspatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

List<Patient> FindByFirstNameAndLastName(String firstName, String lastName);
}
