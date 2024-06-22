package msdiabeterisk.service;

import msdiabeterisk.beans.GenderBeans;
import msdiabeterisk.beans.NoteBeans;
import msdiabeterisk.beans.PatientBeans;
import msdiabeterisk.model.DiabetesRiskLevel;
import msdiabeterisk.proxies.NoteProxy;
import msdiabeterisk.proxies.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DiabetesRiskServiceImplTest {

    @Mock
    private NoteProxy noteProxy;

    @Mock
    private PatientProxy patientProxy;

    @InjectMocks
    private DiabetesRiskServiceImpl diabetesRiskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateDiabetesRisk_NoTriggers() {
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient.setGender(GenderBeans.M);

        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(Collections.emptyList());
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        DiabetesRiskLevel riskLevel = diabetesRiskService.calculateDiabetesRisk(patientId);
        assertEquals(DiabetesRiskLevel.NONE, riskLevel);
    }

    @Test
    public void testCalculateDiabetesRisk_AgeAbove30_Borderline() {
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient.setGender(GenderBeans.M);

        List<NoteBeans> notes = Arrays.asList(
                new NoteBeans("note1", patientId, "PatientName", "hémoglobine a1c"),
                new NoteBeans("note2", patientId, "PatientName", "microalbumine")
        );

        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        DiabetesRiskLevel riskLevel = diabetesRiskService.calculateDiabetesRisk(patientId);
        assertEquals(DiabetesRiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    public void testCalculateDiabetesRisk_AgeAbove30_InDanger() {
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient.setGender(GenderBeans.M);

        List<NoteBeans> notes = Arrays.asList(
                new NoteBeans("note1", patientId, "PatientName", "hémoglobine a1c"),
                new NoteBeans("note2", patientId, "PatientName", "microalbumine"),
                new NoteBeans("note3", patientId, "PatientName", "taille"),
                new NoteBeans("note4", patientId, "PatientName", "poids"),
                new NoteBeans("note5", patientId, "PatientName", "fumeur"),
                new NoteBeans("note6", patientId, "PatientName", "cholestérol")
        );

        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        DiabetesRiskLevel riskLevel = diabetesRiskService.calculateDiabetesRisk(patientId);
        assertEquals(DiabetesRiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    public void testCalculateDiabetesRisk_AgeAbove30_EarlyOnset() {
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient.setGender(GenderBeans.M);

        List<NoteBeans> notes = Arrays.asList(
                new NoteBeans("note1", patientId, "PatientName", "hémoglobine a1c"),
                new NoteBeans("note2", patientId, "PatientName", "microalbumine"),
                new NoteBeans("note3", patientId, "PatientName", "taille"),
                new NoteBeans("note4", patientId, "PatientName", "poids"),
                new NoteBeans("note5", patientId, "PatientName", "fumeur"),
                new NoteBeans("note6", patientId, "PatientName", "cholestérol"),
                new NoteBeans("note7", patientId, "PatientName", "vertiges"),
                new NoteBeans("note8", patientId, "PatientName", "rechute")
        );

        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        DiabetesRiskLevel riskLevel = diabetesRiskService.calculateDiabetesRisk(patientId);
        assertEquals(DiabetesRiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    public void testCalculateDiabetesRisk_AgeBelow30_Male_EarlyOnset() {
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        patient.setDateOfBirth(LocalDate.of(2000, 1, 1));
        patient.setGender(GenderBeans.M);

        List<NoteBeans> notes = Arrays.asList(
                new NoteBeans("note1", patientId, "PatientName", "hémoglobine a1c"),
                new NoteBeans("note2", patientId, "PatientName", "microalbumine"),
                new NoteBeans("note3", patientId, "PatientName", "taille"),
                new NoteBeans("note4", patientId, "PatientName", "poids"),
                new NoteBeans("note5", patientId, "PatientName", "fumeur")
        );

        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        DiabetesRiskLevel riskLevel = diabetesRiskService.calculateDiabetesRisk(patientId);
        assertEquals(DiabetesRiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    public void testCalculateDiabetesRisk_AgeBelow30_Female_InDanger() {
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        patient.setDateOfBirth(LocalDate.of(2000, 1, 1));
        patient.setGender(GenderBeans.F);

        List<NoteBeans> notes = Arrays.asList(
                new NoteBeans("note1", patientId, "PatientName", "hémoglobine a1c"),
                new NoteBeans("note2", patientId, "PatientName", "microalbumine"),
                new NoteBeans("note3", patientId, "PatientName", "taille"),
                new NoteBeans("note4", patientId, "PatientName", "poids")
        );

        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        DiabetesRiskLevel riskLevel = diabetesRiskService.calculateDiabetesRisk(patientId);
        assertEquals(DiabetesRiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    public void testCalculateDiabetesRisk_AgeBelow30_Female_EarlyOnset() {
        int patientId = 1;
        PatientBeans patient = new PatientBeans();
        patient.setId(patientId);
        patient.setDateOfBirth(LocalDate.of(2000, 1, 1));
        patient.setGender(GenderBeans.F);

        List<NoteBeans> notes = Arrays.asList(
                new NoteBeans("note1", patientId, "PatientName", "hémoglobine a1c"),
                new NoteBeans("note2", patientId, "PatientName", "microalbumine"),
                new NoteBeans("note3", patientId, "PatientName", "taille"),
                new NoteBeans("note4", patientId, "PatientName", "poids"),
                new NoteBeans("note5", patientId, "PatientName", "fumeur"),
                new NoteBeans("note6", patientId, "PatientName", "cholestérol"),
                new NoteBeans("note7", patientId, "PatientName", "vertiges")
        );

        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        when(patientProxy.getPatientById(patientId)).thenReturn(patient);

        DiabetesRiskLevel riskLevel = diabetesRiskService.calculateDiabetesRisk(patientId);
        assertEquals(DiabetesRiskLevel.EARLY_ONSET, riskLevel);
    }
}
