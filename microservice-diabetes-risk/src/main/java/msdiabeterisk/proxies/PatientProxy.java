package msdiabeterisk.proxies;

import msdiabeterisk.beans.PatientBeans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "microservice-gateway",
        url = "http://localhost:8181"
)
public interface PatientProxy {
    @GetMapping({"/api/patient/{id}"})
    PatientBeans getPatientById(@PathVariable("id") int id);
}

