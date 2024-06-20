package msdiabeterisk.service;


import msdiabeterisk.model.DiabetesRiskLevel;
import org.springframework.stereotype.Service;

@Service
public interface DiabeteRiskService {
    DiabetesRiskLevel calculateDiabetesRisk(int patientId);
}

