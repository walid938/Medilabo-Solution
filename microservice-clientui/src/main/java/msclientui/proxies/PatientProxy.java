package msclientui.proxies;

import msclientui.beans.PatientBeans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservice-gateway", url = "microservice-gateway:8181")
public interface PatientProxy {

    @GetMapping("/api/patient")
    List<PatientBeans> getAllPatients();

    @GetMapping("/api/patient/{id}")
    PatientBeans getPatientById(@PathVariable("id") int id);

    @GetMapping("/api/patient/search")
    List<PatientBeans> searchPatients(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName);

    @PostMapping("/api/patient")
    PatientBeans createPatient(@RequestBody PatientBeans patientBeans);

    @PutMapping("/api/patient/{id}")
    PatientBeans updatePatient(@PathVariable("id") int id, @RequestBody PatientBeans patient);

    @DeleteMapping("/api/patient/{id}")
    void deletePatient(@PathVariable("id") int id);
}
