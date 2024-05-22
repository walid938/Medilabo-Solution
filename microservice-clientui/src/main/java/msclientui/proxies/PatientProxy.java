package msclientui.proxies;

import msclientui.beans.PatientBeans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservice-patient", url="localhost:8080/api/patient")
public interface PatientProxy {

    @GetMapping
    List<PatientBeans> getAllPatients();

    @GetMapping("/{id}")
    PatientBeans getPatientById(@PathVariable("id") Long id);

    @GetMapping("/search")
    List<PatientBeans> searchPatients(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName);

    @PostMapping
    PatientBeans createPatient(@RequestBody PatientBeans patientBeans);

    @PutMapping("/{id}")
    PatientBeans updatePatient(@PathVariable("id") Long id, @RequestBody PatientBeans patient);

    @DeleteMapping("/{id}")
    void deletePatient(@PathVariable("id") Long id);
}
