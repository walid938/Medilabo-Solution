package msdiabeterisk.proxies;

import msdiabeterisk.beans.NoteBeans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "microservice-gateway",
        url = "http://localhost:8181"
)
public interface NoteProxy {
    @GetMapping({"/api/notes/patient/{patientId}"})
    List<NoteBeans> getNotesByPatientId(@PathVariable int patientId);
}
