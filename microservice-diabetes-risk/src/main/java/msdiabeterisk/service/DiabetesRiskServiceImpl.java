package msdiabeterisk.service;


import msdiabeterisk.beans.GenderBeans;
import msdiabeterisk.beans.NoteBeans;
import msdiabeterisk.beans.PatientBeans;
import msdiabeterisk.model.DiabetesRiskLevel;
import msdiabeterisk.proxies.NoteProxy;
import msdiabeterisk.proxies.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Stream;


@Service
public class DiabetesRiskServiceImpl implements DiabeteRiskService {

    private static final Set<String> TRIGGER_TERMS = new HashSet<>(Arrays.asList(
            "hémoglobine a1c", "microalbumine", "taille", "poids",
            "fumeur", "fumeuse", "anormal", "cholestérol",
            "vertiges", "rechute", "réaction", "anticorps"
    ));

    private final NoteProxy noteProxy;
    private final PatientProxy patientProxy;

    @Autowired
    public DiabetesRiskServiceImpl(NoteProxy noteProxy, PatientProxy patientProxy) {
        this.noteProxy = noteProxy;
        this.patientProxy = patientProxy;
    }

    @Override
    public DiabetesRiskLevel calculateDiabetesRisk(int patientId) {
        List<NoteBeans> notes = noteProxy.getNotesByPatientId(patientId);
        PatientBeans patient = patientProxy.getPatientById(patientId);

        long triggerCount = countTriggerTerms(notes);
        int age = calculateAge(patient.getDateOfBirth());
        GenderBeans gender = patient.getGender();

        if (triggerCount == 0) {
            return DiabetesRiskLevel.NONE;
        }

        if (age > 30) {
            if (triggerCount >= 2 && triggerCount < 6) {
                return DiabetesRiskLevel.BORDERLINE;
            } else if (triggerCount >= 6 && triggerCount < 8) {
                return DiabetesRiskLevel.IN_DANGER;
            } else if (triggerCount >= 8) {
                return DiabetesRiskLevel.EARLY_ONSET;
            }
        } else if (age < 30) {
            if (gender == GenderBeans.M) {
                if (triggerCount >= 3 && triggerCount < 5) {
                    return DiabetesRiskLevel.IN_DANGER;
                } else if (triggerCount >= 5) {
                    return DiabetesRiskLevel.EARLY_ONSET;
                }
            } else if (gender == GenderBeans.F) {
                if (triggerCount >= 4 && triggerCount < 7) {
                    return DiabetesRiskLevel.IN_DANGER;
                } else if (triggerCount >= 7) {
                    return DiabetesRiskLevel.EARLY_ONSET;
                }
            }
        }

        return DiabetesRiskLevel.NONE;
    }

    private long countTriggerTerms(List<NoteBeans> notes) {
        return notes.stream()
                .map(note -> {
                    String content = note.getNoteContent();
                    return content != null ? content.toLowerCase() : "";
                })
                .flatMap(noteContent -> TRIGGER_TERMS.stream().filter(noteContent::contains))
                .count();
    }


    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        return Period.between(dateOfBirth, now).getYears();
    }
}
