package com.surecost.drugapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surecost.drugapi.model.Drug;
import com.surecost.drugapi.service.DrugService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for DrugController.
 */
@WebMvcTest(DrugController.class)
class DrugControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DrugService drugService;

    private UUID testUid;
    private Drug testDrug;

    @BeforeEach
    void setUp() {
        testUid = UUID.randomUUID();
        testDrug = new Drug(testUid, "Test Manufacturer", "Test Drug", 100, new BigDecimal("25.99"));
    }

    @Test
    void createDrug_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        when(drugService.createDrug(any(Drug.class))).thenReturn(testDrug);

        // Act & Assert
        mockMvc.perform(post("/api/drugs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDrug)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uid").value(testUid.toString()));

        verify(drugService).createDrug(any(Drug.class));
    }

    @Test
    void getDrugByUid_WhenDrugExists_ShouldReturnDrug() throws Exception {
        // Arrange
        when(drugService.getDrugByUid(testUid)).thenReturn(Optional.of(testDrug));

        // Act & Assert
        mockMvc.perform(get("/api/drugs/{uid}", testUid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value(testUid.toString()));

        verify(drugService).getDrugByUid(testUid);
    }

    @Test
    void getAllDrugs_ShouldReturnAllDrugs() throws Exception {
        // Arrange
        when(drugService.getAllDrugs()).thenReturn(Arrays.asList(testDrug));

        // Act & Assert
        mockMvc.perform(get("/api/drugs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uid").value(testUid.toString()));

        verify(drugService).getAllDrugs();
    }
}