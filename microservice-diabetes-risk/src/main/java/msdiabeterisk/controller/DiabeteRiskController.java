package msdiabeterisk.controller;

import msdiabeterisk.service.DiabeteRiskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/api/diabeterisk"})
public class DiabeteRiskController {
    DiabeteRiskService diabeteRiskService;

    public DiabeteRiskController(DiabeteRiskService diabeteRiskService) {
        this.diabeteRiskService = diabeteRiskService;
    }

    @GetMapping({"/diabete-risk/{id}"})
    public ResponseEntity calculateDiabetesRisk(@PathVariable("id") int patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.diabeteRiskService.calculateDiabetesRisk(patientId));
    }
}

