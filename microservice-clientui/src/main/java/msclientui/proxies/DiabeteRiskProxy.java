package msclientui.proxies;


import msclientui.beans.DiabeteRiskLevelBeans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "microservice-gateway",
        url = "microservice-gateway:8181"
)
public interface DiabeteRiskProxy {

    @GetMapping(value = "api/diabeterisk/diabete-risk/{id}")
    DiabeteRiskLevelBeans calculateDiabetesRisk(@PathVariable("id") int patientId);
}

