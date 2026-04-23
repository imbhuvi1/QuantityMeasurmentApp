package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.dto.TwoQuantityRequest;
import com.apps.quantitymeasurement.service.AnonymousCalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureTestMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestMvc
@ActiveProfiles("test")
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnonymousCalculatorService anonymousCalculatorService;

    @Test
    void testCompareQuantities() throws Exception {
        // Arrange
        QuantityDTO q1 = new QuantityDTO(12.0, "INCHES", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTH");
        TwoQuantityRequest request = new TwoQuantityRequest();
        request.setQ1(q1);
        request.setQ2(q2);

        when(anonymousCalculatorService.compare(any(QuantityDTO.class), any(QuantityDTO.class)))
                .thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/quantity/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    void testConvertQuantities() throws Exception {
        // Arrange
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(0.0, "INCHES", "LENGTH");
        TwoQuantityRequest request = new TwoQuantityRequest();
        request.setQ1(q1);
        request.setQ2(q2);

        QuantityDTO expectedResult = new QuantityDTO(12.0, "INCHES", "LENGTH");
        when(anonymousCalculatorService.convert(any(QuantityDTO.class), any(QuantityDTO.class)))
                .thenReturn(expectedResult);

        // Act & Assert
        mockMvc.perform(post("/api/quantity/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.value").value(12.0))
                .andExpect(jsonPath("$.data.unit").value("INCHES"));
    }

    @Test
    void testAddQuantities() throws Exception {
        // Arrange
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LENGTH");
        TwoQuantityRequest request = new TwoQuantityRequest();
        request.setQ1(q1);
        request.setQ2(q2);

        QuantityDTO expectedResult = new QuantityDTO(3.0, "FEET", "LENGTH");
        when(anonymousCalculatorService.add(any(QuantityDTO.class), any(QuantityDTO.class)))
                .thenReturn(expectedResult);

        // Act & Assert
        mockMvc.perform(post("/api/quantity/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.value").value(3.0))
                .andExpect(jsonPath("$.data.unit").value("FEET"));
    }
}