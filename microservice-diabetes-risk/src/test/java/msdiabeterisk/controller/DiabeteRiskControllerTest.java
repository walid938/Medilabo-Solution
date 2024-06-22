package msdiabeterisk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import msdiabeterisk.model.DiabetesRiskLevel;
import msdiabeterisk.service.DiabeteRiskService;
import msdiabeterisk.service.DiabetesRiskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DiabeteRiskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DiabeteRiskControllerTest {

    @MockBean
    DiabetesRiskServiceImpl diabetesRiskServiceImpl;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void calculateDiabetesRisk() throws Exception {
        int patientId = 1;
        DiabetesRiskLevel diabetesRiskLevel = DiabetesRiskLevel.NONE;
        Mockito.when(diabetesRiskServiceImpl.calculateDiabetesRisk(patientId)).thenReturn(diabetesRiskLevel);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/diabeterisk/diabete-risk/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(diabetesRiskLevel)));

        Mockito.verify(diabetesRiskServiceImpl, Mockito.times(1)).calculateDiabetesRisk(patientId);
    }
}